package com.coding.boot3.integrated;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Security 默认行为：
 * 1、SecurityAutoConfiguration => 导入 SpringBootWebSecurityConfiguration => 定义 SecurityFilterChain => 拦截所有请求 【非响应式】
 * 2、SecurityFilterAutoConfiguration
 * 3、ReactiveSecurityAutoConfiguration => 条件 EnableWebFluxSecurity 开启时 => 导入 ServerHttpSecurityConfiguration
 * 4、MethodSecurityAspectJAutoProxyRegistrar
 */
@SpringBootApplication
public class Boot319Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot319Application.class, args);
    }

}