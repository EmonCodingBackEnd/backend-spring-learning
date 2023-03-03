package com.coding.springboot2.webadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//@ServletComponentScan
@SpringBootApplication
public class Boot03Application {

    public static void main(String[] args) {
        // 1、返回我们IOIC容器
        ConfigurableApplicationContext run = SpringApplication.run(Boot03Application.class);
        // 2、查看容器里面的组件
        // String[] names = run.getBeanDefinitionNames();
        // for (String name : names) {
        // System.out.println(name);
        // }
    }
}
