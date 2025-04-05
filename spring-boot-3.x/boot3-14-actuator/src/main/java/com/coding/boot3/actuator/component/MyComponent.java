package com.coding.boot3.actuator.component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MyComponent {

    public int check() {
        // 业务代码判断这个组件是否应该是存活状态
        return new Random().nextInt(2);
    }
}
