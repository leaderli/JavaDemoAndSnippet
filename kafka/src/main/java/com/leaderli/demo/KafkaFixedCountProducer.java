package com.leaderli.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class KafkaFixedCountProducer {
    // 基础配置（与消费者保持一致，根据实际环境修改）
    private static final String BOOTSTRAP_SERVERS = "192.168.91.131:9092";
    private static final String TOPIC1 = "li_topic_1";
    private static final String TOPIC2 = "li_topic_2";
    private static final int THREAD_NUM = 2; // 1线程对应1个Topic，并行生成
    private static final long MSG_COUNT_PER_TOPIC = 10000; // 每个Topic生成10000条
    private static final String MSG_CONTENT_TEMPLATE = "【%s】消息ID：%d | 内容：固定数量测试数据 | 时间戳：%d";

    // 全局统计（原子类确保多线程安全）
    private static final AtomicLong TOTAL_MSG_COUNT = new AtomicLong(0);
    private static final AtomicLong TOPIC1_MSG_COUNT = new AtomicLong(0);
    private static final AtomicLong TOPIC2_MSG_COUNT = new AtomicLong(0);

    private final ExecutorService producerPool;
    private final long startTime; // 记录启动时间，计算生成速度

    public KafkaFixedCountProducer() {
        this.producerPool = Executors.newFixedThreadPool(THREAD_NUM);
        this.startTime = System.currentTimeMillis();
    }

    /**
     * 启动多线程生产者：每个Topic独立线程，生成10000条后停止
     */
    public void startFixedCountProduce() {
        System.out.println("=== Kafka 固定数量生产者启动 ===");
        System.out.printf("配置：Broker=%s | 目标Topic=%s、%s | 每个Topic生成%s条 | 无生产延迟%n",
                BOOTSTRAP_SERVERS, TOPIC1, TOPIC2, MSG_COUNT_PER_TOPIC);

        // 线程1：向li_topic_1生成10000条
        producerPool.submit(() -> produceFixedCount(TOPIC1, TOPIC1_MSG_COUNT));
        // 线程2：向li_topic_2生成10000条
        producerPool.submit(() -> produceFixedCount(TOPIC2, TOPIC2_MSG_COUNT));

        // 启动实时统计线程：每秒打印一次进度（避免日志刷屏）
        startRealTimeStatsPrinter();
    }

    /**
     * 核心方法：向指定Topic生成固定数量数据（无延迟）
     * @param targetTopic 目标Topic
     * @param topicCounter 该Topic的计数（控制生成数量）
     */
    private void produceFixedCount(String targetTopic, AtomicLong topicCounter) {
        KafkaProducer<String, String> producer = createHighSpeedProducer(); // 高吞吐配置
        String threadName = Thread.currentThread().getName();
        long successCount = 0; // 实际成功发送的数量（排除失败重试）

        try {
            // 循环生成，直到达到10000条成功发送
            while (successCount < MSG_COUNT_PER_TOPIC) {
                // 计算当前要生成的消息ID（确保连续不重复）
                long currentMsgId = topicCounter.incrementAndGet();
                // 防止重试导致超量：若计数超过10000，提前退出
                if (currentMsgId > MSG_COUNT_PER_TOPIC) {
                    break;
                }

                // 构建消息内容
                long timestamp = System.currentTimeMillis();
                String msgContent = String.format(MSG_CONTENT_TEMPLATE, targetTopic, currentMsgId, timestamp);

                // 发送消息（key用msgId哈希，确保分区均匀分配）
                ProducerRecord<String, String> record = new ProducerRecord<>(
                        targetTopic,
                        String.valueOf(currentMsgId), // key：保证不同消息分散到不同分区
                        msgContent
                );

                // 同步发送（重试机制确保成功，无延迟连续生成）
                RecordMetadata metadata = producer.send(record).get();
                successCount++;
                TOTAL_MSG_COUNT.incrementAndGet();

                // 每1000条打印一次进度（避免日志过多）
                if (successCount % 1000 == 0) {
                    System.out.printf("[%s] %s 进度：%d/%d条 | 分区：%d | 全局累计：%d条%n",
                            threadName, targetTopic, successCount, MSG_COUNT_PER_TOPIC,
                            metadata.partition(), TOTAL_MSG_COUNT.get());
                }
            }

            // 单个Topic生成完成，打印统计
            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            System.out.printf("\n[%s] %s 生成完成！共成功发送%d条 | 耗时：%d秒 | 平均速度：%.1f条/秒%n",
                    threadName, targetTopic, successCount, elapsedTime,
                    elapsedTime > 0 ? (double) successCount / elapsedTime : successCount);

        } catch (InterruptedException e) {
            System.out.printf("[%s] %s 生产线程被中断，已生成%d条（未完成）%n",
                    threadName, targetTopic, successCount);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.printf("[%s] %s 生成失败（已生成%d条）：%s%n",
                    threadName, targetTopic, successCount, e.getMessage());
        } finally {
            producer.close(); // 关闭生产者，释放连接资源
            System.out.printf("[%s] %s 生产线程关闭%n", threadName, targetTopic);
        }
    }

    /**
     * 创建高吞吐生产者配置（无延迟，优化发送效率）
     */
    private KafkaProducer<String, String> createHighSpeedProducer() {
        Properties props = new Properties();
        // 基础配置：Broker地址 + 序列化器（与消费者反序列化器对应）
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 高吞吐优化：关闭延迟 + 批量压缩
        props.put(ProducerConfig.ACKS_CONFIG, "1"); // 1个副本确认，平衡速度与可靠性
        props.put(ProducerConfig.RETRIES_CONFIG, 3); // 发送失败重试3次，避免数据丢失
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 32768); // 批量大小32KB，提升吞吐
        props.put(ProducerConfig.LINGER_MS_CONFIG, 0); // linger=0：不等待，有消息立即发送（关闭延迟）
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 67108864); // 缓冲区64MB，避免阻塞
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy"); // 启用snappy压缩，减少网络传输

        return new KafkaProducer<>(props);
    }

    /**
     * 实时统计线程：每秒覆盖式打印进度，避免日志刷屏
     */
    private void startRealTimeStatsPrinter() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            // 计算各Topic进度与全局速度
            long topic1Done = TOPIC1_MSG_COUNT.get();
            long topic2Done = TOPIC2_MSG_COUNT.get();
            double topic1Percent = (double) topic1Done / MSG_COUNT_PER_TOPIC * 100;
            double topic2Percent = (double) topic2Done / MSG_COUNT_PER_TOPIC * 100;
            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            double globalSpeed = elapsedTime > 0 ? (double) TOTAL_MSG_COUNT.get() / elapsedTime : 0;

            // 覆盖式打印（\r回车符实现单行更新）
            System.out.printf("\r=== 全局进度（每秒更新）===" +
                            " | %s：%d/%d条(%.1f%%)" +
                            " | %s：%d/%d条(%.1f%%)" +
                            " | 全局累计：%d条" +
                            " | 平均速度：%.1f条/秒",
                    TOPIC1, topic1Done, MSG_COUNT_PER_TOPIC, topic1Percent,
                    TOPIC2, topic2Done, MSG_COUNT_PER_TOPIC, topic2Percent,
                    TOTAL_MSG_COUNT.get(), globalSpeed);

            // 所有Topic生成完成，关闭统计线程
            if (topic1Done >= MSG_COUNT_PER_TOPIC && topic2Done >= MSG_COUNT_PER_TOPIC) {
                System.out.println("\n\n=== 所有Topic均已生成10000条数据，统计线程关闭 ===");
                // 关闭统计线程池
                ((ExecutorService) Thread.currentThread().getThreadGroup().getParent()).shutdown();
            }
        }, 1, 1, TimeUnit.SECONDS); // 延迟1秒启动，每秒更新一次
    }

    /**
     * 等待所有生产线程完成，优雅关闭资源
     */
    public void waitAndShutdown() {
        try {
            // 等待所有线程完成（最多等待5分钟，防止无限阻塞）
            if (producerPool.awaitTermination(5, TimeUnit.MINUTES)) {
                long totalElapsed = (System.currentTimeMillis() - startTime) / 1000;
                System.out.println("\n=== 所有生产线程完成 ===");
                System.out.printf("最终统计：全局总计%d条 | %s：%d条 | %s：%d条 | 总耗时：%d秒 | 总平均速度：%.1f条/秒%n",
                        TOTAL_MSG_COUNT.get(), TOPIC1, TOPIC1_MSG_COUNT.get(),
                        TOPIC2, TOPIC2_MSG_COUNT.get(), totalElapsed,
                        totalElapsed > 0 ? (double) TOTAL_MSG_COUNT.get() / totalElapsed : 0);
            } else {
                producerPool.shutdownNow();
                System.out.println("\n=== 生产线程超时，强制关闭 ===");
            }
        } catch (InterruptedException e) {
            producerPool.shutdownNow();
            Thread.currentThread().interrupt();
            System.out.println("\n=== 等待生产线程时被中断，强制关闭 ===");
        }
    }

    // 主方法：程序入口
    public static void main(String[] args) {
        KafkaFixedCountProducer producer = new KafkaFixedCountProducer();
        producer.startFixedCountProduce();
        // 等待所有Topic生成完成后再关闭程序
        producer.waitAndShutdown();
    }
}
