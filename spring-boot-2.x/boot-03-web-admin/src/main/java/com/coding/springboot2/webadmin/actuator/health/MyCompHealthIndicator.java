package com.coding.springboot2.webadmin.actuator.health;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class MyCompHealthIndicator extends AbstractHealthIndicator {

    /**
     * 真是的检查方法
     * 
     * @param builder
     * @throws Exception
     */
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (1 == 1) {
            builder.up();
            map.put("count", 1);
            map.put("ms", 100);
        } else {
            builder.status(Status.OUT_OF_SERVICE);
            map.put("err", "连接超时");
            map.put("ms", 3000);
        }
        builder.withDetail("code", 100).withDetails(map);
    }
}
