package com.coding.boot3.msg;

import com.coding.boot3.msg.entity.Person;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StopWatch;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
class Boot312ApplicationTests {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    void contextLoads() {
        StopWatch sw = new StopWatch();

        sw.start();
        CompletableFuture[] futures = new CompletableFuture[10000];
        for (int i = 0; i < 10000; i++) {
            CompletableFuture future = kafkaTemplate.send("news", "haha", "hello kafka");
            futures[i] = future;
        }
        CompletableFuture.allOf(futures).join();
        sw.stop();

        System.out.println("cost time:" + sw.getTotalTimeMillis());
    }

    @Test
    void send() {
        CompletableFuture future = kafkaTemplate.send("news", "person", new Person(1L, "张三", "aaa@qq.com"));
        future.join();
        System.out.println("消息发送成功！");
    }

}
