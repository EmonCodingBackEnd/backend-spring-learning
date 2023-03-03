package com.coding.springboot2.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coding.emon.hello.service.HelloService;

@Configuration
public class MyConfig {

    @Bean
    public HelloService helloService() {
        HelloService helloService = new HelloService();
        return helloService;
    }
}
