package com.coding.boot3.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Security场景的自动配置类：SecurityAutoConfiguration、SecurityFilterAutoConfiguration
 * 1、Security的所有配置都在 SecurityProperties：以 spring.security 开头
 * 2、SecurityAutoConfiguration->SpringBootWebSecurityConfiguration->默认SecurityFilterChain组件：
 *  - 所有请求都需要认证（登录）
 *  - 开启表单登录：SpringSecurity 提供一个默认的登录页，未经认证的所有请求都会跳转到登录页
 *  - httpBasic方式登录
 * 3、SecurityAutoConfiguration->SpringBootWebSecurityConfiguration->@EnableWebSecurity 自动生效
 *  - WebSecurityConfiguration 生效：Web安全配置
 *  - HttpSecurityConfiguration 生效：http安全规则
 *  - @EnableGlobalAuthentication 生效：全局认证配置
 *      - AuthenticationConfiguration 生效：认证配置
 * 4、UserDetailsServiceAutoConfiguration 定义用户查询服务
 */
@SpringBootApplication
public class Boot313Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot313Application.class, args);
    }

}