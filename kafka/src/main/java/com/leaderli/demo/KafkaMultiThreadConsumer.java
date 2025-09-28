package com.leaderli.demo;

import io.leaderli.litool.core.util.ThreadUtil;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class KafkaMultiThreadConsumer {
    private static final String BOOTSTRAP_SERVERS = "192.168.91.131:9092";
    private static final String TOPIC_NAME = "li_topic_1";
    private static final String TOPIC_NAME2 = "li_topic_2";
    private static final String TOPIC_NAME3 = "li_topic_3";
    private static final String TOPIC_NAME4 = "li_topic_4";
    private static final String GROUP_ID = "li_consumer_group";
    private static final int CONSUMER_THREADS = 4;
    private static final AtomicLong GLOBAL_TOTAL = new AtomicLong(0);
    private static final long IDLE_MESSAGE_INTERVAL = 10000;
    private static final int BATCH_SIZE = 5;
    private static final long PROCESS_INTERVAL = 50;
    private static final AtomicBoolean ALL_CONSUMERS_FINISHED = new AtomicBoolean(false);
    private static final AtomicInteger ACTIVE_CONSUMERS = new AtomicInteger(CONSUMER_THREADS * 4);
    private static final int ALL = 80000;

    private final ExecutorService executorService;
    private static Map<String, String> msg = new HashMap<>();
    private static Map<String, Long> serverCount = new HashMap<>();

    public KafkaMultiThreadConsumer() {
        this.executorService = Executors.newFixedThreadPool(CONSUMER_THREADS);
    }

    public void start() {
        System.out.println("启动 " + CONSUMER_THREADS + " 个消费者线程...");
        System.out.println("配置: 每次处理" + BATCH_SIZE + "条数据，间隔" + PROCESS_INTERVAL / 1000 + "秒");

        for (int i = 0; i < CONSUMER_THREADS; i++) {
            int threadID = i;
            executorService.submit(() -> {
                ThreadUtil.sleep(threadID * 1000);
                Thread thread = new Thread(new ConsumerWorker(threadID, TOPIC_NAME));
                thread.setName(threadID + "_" + 1);
                thread.start();
                thread = new Thread(new ConsumerWorker(threadID, TOPIC_NAME2));
                thread.setName(threadID + "_" + 2);
                thread.start();
                thread = new Thread(new ConsumerWorker(threadID, TOPIC_NAME3));
                thread.setName(threadID + "_" + 3);
                thread.start();
                thread = new Thread(new ConsumerWorker(threadID, TOPIC_NAME4));
                thread.setName(threadID + "_" + 4);
                thread.start();
            });
        }
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    private static class ConsumerWorker implements Runnable {
        private final KafkaConsumer<String, String> consumer;
        private long totalConsumedCount = 0;
        private long lastConsumeTime;
        private final String topic;
        private int server;

        public ConsumerWorker(int server, String topic) {
            this.server = server;
            this.topic = topic;
            this.consumer = createConsumer();
            this.lastConsumeTime = System.currentTimeMillis();
        }

        private KafkaConsumer<String, String> createConsumer() {
            Properties props = new Properties();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
            props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, BATCH_SIZE * 2);
            return new KafkaConsumer<>(props);
        }

        @Override
        public void run() {
            try {
                consumer.subscribe(Collections.singletonList(topic));
                System.out.println("消费者线程 " + Thread.currentThread().getId() + " 开始运行");

                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                    processBatchRecords(records);
                    checkAndPrintIdleMessage();
                    Thread.sleep(PROCESS_INTERVAL);
                }
            } catch (Exception ignored) {
            } finally {
                long globalCount = GLOBAL_TOTAL.get();
                double percentage = totalConsumedCount * 100.0 / ALL;
                String format = String.format(
                        "服务器:%d 消费者线程 %s 已关闭  已消费总数: %d 占比: %.2f%% 总条数:%d",
                        server,
                        Thread.currentThread().getName(),
                        totalConsumedCount,
                        percentage,
                        globalCount
                );
                System.out.println(format);
                consumer.close();
            }
        }

        private void processBatchRecords(ConsumerRecords<String, String> records) {
            for (ConsumerRecord<String, String> record : records) {
                processRecord(record);
            }
        }

        private void checkAndPrintIdleMessage() {
            // 空实现
        }

        private void processRecord(ConsumerRecord<String, String> record) {
            totalConsumedCount++;
            long globalCount = GLOBAL_TOTAL.incrementAndGet();

            double percentage = (totalConsumedCount * 100.0) / ALL;
            String format = String.format(
                    "服务器:%d 线程 %s  已消费总数: %d 占比: %.2f%% 总条数:%d",
                    server,
                    record.topic() + "_" + record.partition(),
                    totalConsumedCount,
                    percentage,
                    globalCount
            );

            System.out.println(format);
            msg.put(server + "_" + record.topic() + "_" + record.partition(), format);
            serverCount.put(server + "_" + record.topic() + "_" + record.partition(), totalConsumedCount);
        }
    }

    public static void printResult() {
        System.out.println("-----------------------------------------------");
        List<String> p = new ArrayList<>();
        msg.forEach((key, value) -> p.add(key + " " + value));

        p.stream().sorted().forEach(System.out::println);
        System.out.println("-----------------------------------------------");
        Map<String, List<Map.Entry<String, String>>> collect = msg.entrySet().stream()
                .collect(Collectors.groupingBy(e -> e.getKey().substring(0, 1)));
        Map<String, List<Map.Entry<String, Long>>> collect2 = serverCount.entrySet().stream()
                .collect(Collectors.groupingBy(e -> e.getKey().substring(0, 1)));

        collect.forEach((k, v) -> {
            long c = 0;
            List<Map.Entry<String, Long>> e2 = collect2.get(k);
            for (Map.Entry<String, Long> e3 : e2) {
                c += e3.getValue();
            }
            System.out.println("服务器:" + k + " 消费分区数:" + v.size() + " " + c + " " + c * 100.0 / ALL + "%");
        });
    }

    public static void main(String[] args) {
        KafkaMultiThreadConsumer consumer = new KafkaMultiThreadConsumer();
        consumer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(consumer::shutdown));
        Runtime.getRuntime().addShutdownHook(new Thread(KafkaMultiThreadConsumer::printResult));
    }
}
