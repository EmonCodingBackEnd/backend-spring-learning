package com.coding.boot3.msg.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AppKafkaConfiguration {

    // 启动会自动创建 topic，因为 kafkaAdmin 默认存在
    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("thing")
                .partitions(1)
                .compact()
                .build();
    }
}
