package com.coding.springboot2.webadmin.liteflow.context;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

@Data
public class CustomContext {

    private AtomicInteger times = new AtomicInteger();

    public int increment() {
        return times.incrementAndGet();
    }
}
