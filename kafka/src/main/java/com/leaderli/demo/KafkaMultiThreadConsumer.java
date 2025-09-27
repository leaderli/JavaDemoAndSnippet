package com.leaderli.demo;

import io.leaderli.litool.core.util.ThreadUtil;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class KafkaMultiThreadConsumer {
    // Kafka 服务器地址
    private static final String BOOTSTRAP_SERVERS = "192.168.91.131:9092";
    // 要消费的主题
    private static final String TOPIC_NAME = "li_topic_1";
    private static final String TOPIC_NAME2 = "li_topic_2";
    // 消费者组 ID
    private static final String GROUP_ID = "li_consumer_group";
    // 线程数量
    private static final int CONSUMER_THREADS = 6;
    // 全局消费总数计数器
    private static final AtomicLong GLOBAL_TOTAL = new AtomicLong(0);
    // 空闲状态提示间隔（毫秒）
    private static final long IDLE_MESSAGE_INTERVAL = 10000;
    // 每次处理的数据量
    private static final int BATCH_SIZE = 5;
    // 处理间隔时间（毫秒）
    private static final long PROCESS_INTERVAL = 50;

    private final ExecutorService executorService;

    public KafkaMultiThreadConsumer() {
        this.executorService = Executors.newFixedThreadPool(CONSUMER_THREADS);
    }

    /**
     * 启动多线程消费者
     */
    public void start() {
        System.out.println("启动 " + CONSUMER_THREADS + " 个消费者线程...");
        System.out.println("配置: 每次处理" + BATCH_SIZE + "条数据，间隔" + PROCESS_INTERVAL / 1000 + "秒");

        // 为每个线程创建一个独立的消费者实例
        for (int i = 0; i < CONSUMER_THREADS; i++) {
            executorService.submit(new ConsumerWorker(i));
        }
    }

    /**
     * 关闭消费者和线程池
     */
    public void shutdown() {
        System.out.println("正在关闭消费者...");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        System.out.println("所有消费者已关闭，全局总消费数: " + GLOBAL_TOTAL.get());
    }

    /**
     * 消费者工作线程
     */
    private static class ConsumerWorker implements Runnable {
        private final int threadId;
        private final KafkaConsumer<String, String> consumer;
        // 线程内消费计数器
        private long totalConsumedCount = 0;
        // 上次消费时间
        private long lastConsumeTime;

        public ConsumerWorker(int threadId) {
            this.threadId = threadId;
            this.consumer = createConsumer();
            this.lastConsumeTime = System.currentTimeMillis();
        }

        /**
         * 创建 Kafka 消费者
         */
        private KafkaConsumer<String, String> createConsumer() {
            Properties props = new Properties();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            // 2. 配置RoundRobinAssignor（核心）
            props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,
                    "org.apache.kafka.clients.consumer.RoundRobinAssignor");
            // 自动提交偏移量
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
            props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

            // 从最早的消息开始消费
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

            // 每次拉取的最大记录数（设置为批量大小的2倍，确保能获取足够数据）
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, BATCH_SIZE * 2);

            return new KafkaConsumer<>(props);
        }

        @Override
        public void run() {
            ThreadUtil.sleep(threadId* 1000L);
            try {
                // 订阅主题
                consumer.subscribe(Arrays.asList(TOPIC_NAME,TOPIC_NAME2));
                System.out.println("消费者线程 " + threadId + " 开始运行");

                // 持续消费消息
                while (!Thread.currentThread().isInterrupted()) {
                    // 拉取消息
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                    // 检查是否有消息
                    // 有消息时更新最后消费时间并批量处理消息
                    processBatchRecords(records);
                    // 处理完一批后等待指定间隔时间
                    checkAndPrintIdleMessage();

                    Thread.sleep(PROCESS_INTERVAL);
                }
            } catch (InterruptedException e) {
                System.out.println("消费者线程 " + threadId + " 被中断");
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println("消费者线程 " + threadId + " 发生错误: " + e.getMessage());
            } finally {

                // 计算当前线程消费占比
                long globalCount = GLOBAL_TOTAL.get();
                double percentage = globalCount > 0 ? (totalConsumedCount * 100.0) / globalCount : 0;
                System.out.printf(
                        "消费者线程 %d 已关闭  已消费总数: %d 占比: %.2f%% 总条数:%d%n",
                        threadId,
                        totalConsumedCount,
                        percentage,
                        globalCount
                );
                consumer.close();
            }
        }

        /**
         * 批量处理消息（每次最多处理BATCH_SIZE条）
         */
        private void processBatchRecords(ConsumerRecords<String, String> records) {
            for (ConsumerRecord<String, String> record : records) {
                // 处理单条消息
                processRecord(record);

            }

        }

        /**
         * 检查并打印空闲状态消息
         */
        private void checkAndPrintIdleMessage() {
            // 计算当前线程消费占比
            long globalCount = GLOBAL_TOTAL.get();
            double percentage = globalCount > 0 ? (totalConsumedCount * 100.0) / globalCount : 0;

            System.out.printf(
                    "线程 %d - 等待消息中... 已消费总数: %d 占比: %.2f%% 总条数:%d%n",
                    threadId,
                    totalConsumedCount,
                    percentage,
                    globalCount
            );
        }

        /**
         * 处理单条消息的方法
         */
        private void processRecord(ConsumerRecord<String, String> record) {
            // 计数器递增
            totalConsumedCount++;
            // 全局计数器递增
            long globalCount = GLOBAL_TOTAL.incrementAndGet();


            // 计算当前线程消费占比
            double percentage = globalCount > 0 ? (totalConsumedCount * 100.0) / globalCount : 0;

            // 打印信息
//            System.out.printf(
//                    "线程 %d - 已消费总数: %d 占比: %.2f%%  | 分区: %d topic: %s%n",
//                    threadId,
//                    totalConsumedCount,
//                    percentage,
//                    record.partition(),
//                    record.topic()
//            );
            // 模拟单条消息处理耗时
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        // 创建并启动多线程消费者
        KafkaMultiThreadConsumer consumer = new KafkaMultiThreadConsumer();
        consumer.start();

        // 注册关闭钩子，在程序退出时优雅关闭消费者
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::shutdown));
    }
}

