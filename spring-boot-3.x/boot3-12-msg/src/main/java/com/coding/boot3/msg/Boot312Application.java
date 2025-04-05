package com.coding.boot3.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * KafkaAutoConfiguration 提供如下功能：
 * 1、KafkaProperties：kafka的所有配置；以spring.kafka开始
 * - bootstrapServers: kafka集群的所有服务器地址
 * - properties: 参数设置
 * - consumer: 消费者
 * - producer: 生产者
 * 2、@EnableKafka：开启kafka在注解驱动功能
 * 3、KafkaTemplate: 用于发送消息
 * 4、KafkaAdmin: 用于管理kafka 主题等
 * 5、@KafkaListener: 用于监听kafka消息
 */
@SpringBootApplication
public class Boot312Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot312Application.class, args);
    }

}