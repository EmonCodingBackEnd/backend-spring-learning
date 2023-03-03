package com.coding.springboot2.helloworld.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 只有在容器中的组件，才有SpringBoot提供的强大功能。
 *
 * 方式一：@Component/@Configuration(proxyBeanMethods = true) + @ConfigurationProperties(prefix = "mycar")
 *
 * 方式二：@EnableConfigurationProperties(Car.class) + @ConfigurationProperties(prefix = "mycar")
 */
@Data
// @Component
@ConfigurationProperties(prefix = "mycar")
public class Car {

    private String brand;
    private Integer price;

}
