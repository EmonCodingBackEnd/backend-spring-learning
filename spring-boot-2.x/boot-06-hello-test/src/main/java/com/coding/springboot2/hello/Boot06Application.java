package com.coding.springboot2.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Boot06Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Boot06Application.class);
    }
}
