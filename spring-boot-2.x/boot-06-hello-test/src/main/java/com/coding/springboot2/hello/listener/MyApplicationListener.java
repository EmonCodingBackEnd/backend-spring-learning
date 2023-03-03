package com.coding.springboot2.hello.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.err.println("多次==>MyApplicationListener ...onApplicationEvent...");
    }
}
