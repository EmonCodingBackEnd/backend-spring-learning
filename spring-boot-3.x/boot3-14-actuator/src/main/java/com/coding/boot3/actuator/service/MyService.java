package com.coding.boot3.actuator.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MyService {


    Counter counter;

    /**
     * 若类只有一个有参构造器，则有参构造器的参赛，默认从容器中获取
     *
     * @param meterRegistry - 注入 meterRegistry 来保存和统计所有指标
     */
    public MyService(MeterRegistry meterRegistry) {
        counter = meterRegistry.counter("myService.method.running.counter");
    }


    public void hello() {
        System.out.println("hello");
        counter.increment();
    }
}
