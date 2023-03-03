package com.coding.springboot2.profile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.coding.springboot2.profile.bean.Color;

@Configuration
public class MyConfig {

    @Profile("prod")
    @Bean
    public Color red() {
        return new Color();
    }

    @Profile("prod")
    @Bean
    public Color green() {
        return new Color();
    }
}
