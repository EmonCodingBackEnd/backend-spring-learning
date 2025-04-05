package com.coding.boot3.integrated.config;

import com.coding.boot3.integrated.component.AppReactiveUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableReactiveMethodSecurity // 开启方法权限控制
@EnableWebFluxSecurity
@Configuration
public class AppSecurityConfiguration {

    @Autowired
    AppReactiveUserDetailsService userDetailsService;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        // 1、定义哪些请求需要认证
        http.authorizeExchange(exchanges ->
                exchanges
                        // 1.1、允许所有人都可以访问静态资源
                        .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // 1.2、剩下的所有请求都需要认证（登录）
                        .anyExchange().authenticated()
        );
        // 2、开启默认的表单登录
        http.formLogin(withDefaults());
        // 3、关闭CSRF（跨站请求伪造）
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        /*
        目前认证：用户名是 user，密码是默认生成的并输出到控制台日志
        期望认证：去数据库查用户名和密码
         */
        // 4、配置认证规则：如何去数据库查询到用户；Spring Security底层使用 ReactiveAuthenticationManager 去查询用户信息
        http.authenticationManager(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService));
        // 构建出安全配置
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
