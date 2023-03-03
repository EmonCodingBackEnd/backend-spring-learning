package com.coding.springboot2.hello.listener;

import org.springframework.boot.CommandLineRunner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Slf4j
@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.err.println("8==>MyCommandLineRunner ...run...");
    }
}
