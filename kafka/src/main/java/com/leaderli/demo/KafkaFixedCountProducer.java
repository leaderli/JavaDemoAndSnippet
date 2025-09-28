package com.leaderli.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class KafkaFixedCountProducer {
    // 基础配置
    private static final String BOOTSTRAP_SERVERS = "192.168.91.131:9092";
    private static final long MSG_COUNT_PER_TOPIC = 10000;
    private static final String MSG_CONTENT_TEMPLATE = "【%s】消息ID：%d | 内容：固定数量测试数据 | 时间戳：%d";

    // 动态topics和计数器
    private final List<String> topics;
    private final Map<String, AtomicLong> topicCounters;
    private static final AtomicLong TOTAL_MSG_COUNT = new AtomicLong(0);

    private final ExecutorService producerPool;
    private final long startTime;

    public KafkaFixedCountProducer(String... topics) {
        if (topics == null || topics.length == 0) {
            throw new IllegalArgumentException("至少需要指定一个topic");
        }
        this.topics = Arrays.asList(topics);
        this.topicCounters = new ConcurrentHashMap<>();
        Arrays.stream(topics).forEach(topic ->
                this.topicCounters.put(topic, new AtomicLong(0))
        );
        this.producerPool = Executors.newFixedThreadPool(topics.length);
        this.startTime = System.currentTimeMillis();
    }

    public void startFixedCountProduce() {
        System.out.println("=== Kafka 固定数量生产者启动 ===");
        System.out.printf("配置：Broker=%s | 目标Topics=%s | 每个Topic生成%s条 | 无生产延迟%n",
                BOOTSTRAP_SERVERS, String.join(", ", topics), MSG_COUNT_PER_TOPIC);

        topics.forEach(topic ->
                producerPool.submit(() -> produceFixedCount(topic, topicCounters.get(topic)))
        );

        startRealTimeStatsPrinter();
    }

    private void produceFixedCount(String targetTopic, AtomicLong topicCounter) {
        KafkaProducer<String, String> producer = createHighSpeedProducer();
        String threadName = Thread.currentThread().getName();
        long successCount = 0;

        try {
            while (successCount < MSG_COUNT_PER_TOPIC) {
                long currentMsgId = topicCounter.incrementAndGet();
                if (currentMsgId > MSG_COUNT_PER_TOPIC) {
                    break;
                }

                long timestamp = System.currentTimeMillis();
                String msgContent = String.format(MSG_CONTENT_TEMPLATE, targetTopic, currentMsgId, timestamp);

                ProducerRecord<String, String> record = new ProducerRecord<>(
                        targetTopic,
                        String.valueOf(currentMsgId),
                        msgContent
                );

                RecordMetadata metadata = producer.send(record).get();
                successCount++;
                TOTAL_MSG_COUNT.incrementAndGet();

                if (successCount % 1000 == 0) {
                    System.out.printf("[%s] %s 进度：%d/%d条 | 分区：%d | 全局累计：%d条%n",
                            threadName, targetTopic, successCount, MSG_COUNT_PER_TOPIC,
                            metadata.partition(), TOTAL_MSG_COUNT.get());
                }
            }

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
            producer.close();
            System.out.printf("[%s] %s 生产线程关闭%n", threadName, targetTopic);
        }
    }

    private KafkaProducer<String, String> createHighSpeedProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 32768);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 67108864);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        return new KafkaProducer<>(props);
    }

    private void startRealTimeStatsPrinter() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            StringBuilder progressFormat = new StringBuilder("\r=== 全局进度（每秒更新）===");
            boolean allFinished = true;

            for (String topic : topics) {
                long topicDone = topicCounters.get(topic).get();
                double topicPercent = (double) topicDone / MSG_COUNT_PER_TOPIC * 100;
                progressFormat.append(String.format(" | %s：%d/%d条(%.1f%%)",
                        topic, topicDone, MSG_COUNT_PER_TOPIC, topicPercent));
                if (topicDone < MSG_COUNT_PER_TOPIC) {
                    allFinished = false;
                }
            }

            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            double globalSpeed = elapsedTime > 0 ? (double) TOTAL_MSG_COUNT.get() / elapsedTime : 0;
            progressFormat.append(String.format(" | 全局累计：%d条 | 平均速度：%.1f条/秒",
                    TOTAL_MSG_COUNT.get(), globalSpeed));

            System.out.print(progressFormat.toString());

            if (allFinished) {
                System.out.println("\n\n=== 所有Topic均已生成" + MSG_COUNT_PER_TOPIC + "条数据，统计线程关闭 ===");
                ((ExecutorService) Thread.currentThread().getThreadGroup().getParent()).shutdown();
                System.exit(0);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void waitAndShutdown() {
        try {
            if (producerPool.awaitTermination(5, TimeUnit.MINUTES)) {
                long totalElapsed = (System.currentTimeMillis() - startTime) / 1000;
                System.out.println("\n=== 所有生产线程完成 ===");

                StringBuilder statsFormat = new StringBuilder("最终统计：全局总计" + TOTAL_MSG_COUNT.get() + "条");
                for (String topic : topics) {
                    statsFormat.append(String.format(" | %s：%d条",
                            topic, topicCounters.get(topic).get()));
                }
                statsFormat.append(String.format(" | 总耗时：%d秒 | 总平均速度：%.1f条/秒",
                        totalElapsed,
                        totalElapsed > 0 ? (double) TOTAL_MSG_COUNT.get() / totalElapsed : 0));

                System.out.println(statsFormat.toString());
                System.exit(0); // 添加这行确保程序退出

            } else {
                producerPool.shutdownNow();
                System.out.println("\n=== 生产线程超时，强制关闭 ===");
                System.exit(0); // 添加这行确保程序退出

            }
        } catch (InterruptedException e) {
            producerPool.shutdownNow();
            Thread.currentThread().interrupt();
            System.out.println("\n=== 等待生产线程时被中断，强制关闭 ===");
            System.exit(0); // 添加这行确保程序退出
        }

    }

    public static void main(String[] args) {
        KafkaFixedCountProducer producer = new KafkaFixedCountProducer("li_topic_1", "li_topic_2","li_topic_3","li_topic_4");
        producer.startFixedCountProduce();
        producer.waitAndShutdown();
    }
}
