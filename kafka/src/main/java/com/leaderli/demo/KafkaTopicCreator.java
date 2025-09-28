package com.leaderli.demo;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaTopicCreator {
    private static final String BOOTSTRAP_SERVERS = "192.168.91.131:9092";
    private static final String TOPIC_PREFIX = "li_topic_";
    private static final int PARTITION_COUNT = 4;
    private static final int REPLICATION_FACTOR = 1;
    private static final int TOPIC_COUNT = 4;

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        try (AdminClient adminClient = AdminClient.create(props)) {
            System.out.println("开始创建topics...");

            for (int i = 1; i <= TOPIC_COUNT; i++) {
                String topicName = TOPIC_PREFIX + i;

                try {
                    // 先尝试删除已存在的topic
                    deleteTopicIfExists(adminClient, topicName);

                    // 等待一秒确保topic完全删除
                    Thread.sleep(1000);

                    // 创建新的topic
                    createTopic(adminClient, topicName);

                } catch (Exception e) {
                    System.err.printf("处理topic %s 时发生错误: %s%n", topicName, e.getMessage());
                }
            }

            System.out.println("所有topics处理完成！");
        }
    }

    private static void deleteTopicIfExists(AdminClient adminClient, String topicName) {
        try {
            // 检查topic是否存在
            if (adminClient.listTopics().names().get().contains(topicName)) {
                System.out.printf("发现已存在的topic: %s，准备删除...%n", topicName);

                DeleteTopicsResult deleteResult = adminClient.deleteTopics(
                        Collections.singleton(topicName)
                );

                deleteResult.all().get();
                System.out.printf("成功删除topic: %s%n", topicName);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.printf("删除topic %s 失败: %s%n", topicName, e.getMessage());
        }
    }

    private static void createTopic(AdminClient adminClient, String topicName) {
        try {
            NewTopic newTopic = new NewTopic(
                    topicName,
                    PARTITION_COUNT,
                    (short) REPLICATION_FACTOR
            );

            CreateTopicsResult result = adminClient.createTopics(
                    Collections.singleton(newTopic)
            );

            result.all().get();
            System.out.printf("成功创建topic: %s (分区数: %d, 副本数: %d)%n",
                    topicName, PARTITION_COUNT, REPLICATION_FACTOR);
        } catch (InterruptedException | ExecutionException e) {
            System.err.printf("创建topic %s 失败: %s%n", topicName, e.getMessage());
        }
    }
}
