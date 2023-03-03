package com.coding.springboot2.webadmin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.coding.springboot2.webadmin.interceptor.LoginInterceptor;

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/", "/login")
            .excludePathPatterns("/css/**", "/js/**", "/images/**", "/fonts/**");
    }

    /*@Bean
    public WebMvcRegistrations webMvcRegistrations() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return WebMvcRegistrations.super.getRequestMappingHandlerMapping();
            }
        };
    }*/

}
