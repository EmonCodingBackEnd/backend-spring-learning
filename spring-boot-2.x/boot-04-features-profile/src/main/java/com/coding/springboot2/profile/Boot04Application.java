package com.coding.springboot2.profile;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class Boot04Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Boot04Application.class);
        ConfigurableEnvironment environment = run.getEnvironment();
        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
        System.out.println(systemEnvironment);
        Map<String, Object> systemProperties = environment.getSystemProperties();
        System.out.println(systemProperties);
    }
}
