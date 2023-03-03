package com.coding.springboot2.webadmin.actuator.metrics;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MyMetrics {
    public MyMetrics(MeterRegistry meterRegistry) {
        Counter counter = meterRegistry.counter("myMetrics.count");
        counter.increment();
    }
}
