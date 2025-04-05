package com.coding.boot3.msg.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class MyTopicListener {
    // 默认仅监听消息队列最新的消息
    @KafkaListener(groupId = "group1", topics = "news")
    public void onMessage1(ConsumerRecord<Object, Object> record) {
        Object key = record.key();
        Object value = record.value();
        System.out.printf("MyTopicListener group1 接收到消息：key=%s,value=%s%n", key, value);
    }

    @KafkaListener(groupId = "group2", topicPartitions = {@TopicPartition(topic = "news",
            partitionOffsets = {@PartitionOffset(partition = "0", initialOffset = "0")})})
    public void onMessage2(ConsumerRecord<Object, Object> record) {
        Object key = record.key();
        Object value = record.value();
        System.out.printf("MyTopicListener group2 接收到消息：key=%s,value=%s%n", key, value);
    }
}
