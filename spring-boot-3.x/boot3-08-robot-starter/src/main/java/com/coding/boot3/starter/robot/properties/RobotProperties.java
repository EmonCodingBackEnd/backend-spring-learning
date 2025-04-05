package com.coding.boot3.starter.robot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "robot")
@Component
public class RobotProperties {
    private String name;
    private String age;
    private String email;
}
