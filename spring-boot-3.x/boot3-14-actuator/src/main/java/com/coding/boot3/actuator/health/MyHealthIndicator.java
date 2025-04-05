package com.coding.boot3.actuator.health;

import com.coding.boot3.actuator.component.MyComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * 1、实现 HealthIndicator 接口来定制组件的健康状态对象（Health）返回
 */
@Component
public class MyHealthIndicator extends AbstractHealthIndicator {

    @Autowired
    private MyComponent component;

    /**
     * 健康检查
     *
     * @param builder the {@link Health.Builder} to report health status and details
     */
    @Override
    protected void doHealthCheck(Health.Builder builder) {
        // 自定义检查方法
        int check = component.check();
        if (check == 0) {
            builder.up().withDetail("code", check).withDetail("msg", "活的很健康").withDetail("data", "我是MyComponent的监控状态").build();
        } else {
            builder.down().withDetail("code", check).withDetail("msg", "error service").withDetail("data", "我是MyComponent的监控状态")
//                    .withException(new RuntimeException())
                    .build();
        }
    }
}
