package com.coding.springboot2.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Boot02Application {

    public static void main(String[] args) {
        // 1、返回我们IOIC容器
        ConfigurableApplicationContext run = SpringApplication.run(Boot02Application.class);
        // 2、查看容器里面的组件
        // String[] names = run.getBeanDefinitionNames();
        // for (String name : names) {
        // System.out.println(name);
        // }

        String[] beanNamesForTypes = run.getBeanNamesForType(WebMvcConfigurer.class);
        System.out.println("==========");
        for (String beanNamesForType : beanNamesForTypes) {
            System.out.println(beanNamesForType);
        }
    }
}
