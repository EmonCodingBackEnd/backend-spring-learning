package com.coding.boot3.web.config;

import com.coding.boot3.web.converter.MyYamlHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.util.List;

//@EnableWebMvc // 禁用 boot 的默认配置
// 自定义静态资源规则：方式一
/*@Configuration // 这是一个配置类，给容器中放一个 WebMvcConfigurer 组件，就能自定义底层
public class MyConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 自己写新的规则，仍旧保留了以前的默认规则
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/staticaliasa/", "classpath:/staticaliasb/")
                .setCacheControl(CacheControl.maxAge(Duration.ofSeconds(1180)));
    }
}*/

// 自定义静态资源规则：方式二
/*@Configuration
public class MyConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/staticaliasa/", "classpath:/staticaliasb/")
                        .setCacheControl(CacheControl.maxAge(Duration.ofSeconds(1180)));
            }
        };
    }
}*/

// 自定义内容协商转换器
/*@Configuration
public class MyConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override //配置一个能把对象转为yaml的messageConverter
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new MyYamlHttpMessageConverter());
            }
        };
    }
}*/
