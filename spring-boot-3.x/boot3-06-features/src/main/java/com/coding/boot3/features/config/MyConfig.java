package com.coding.boot3.features.config;

import com.coding.boot3.features.bean.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("test") // 只有指定环境被激活整个类的所有配置才能生效
//@PropertySource("classpath:aaa.properties") // 低于配置文件的优先级，注意profile=test时才启用
@Configuration
public class MyConfig {

    @Profile("dev")
    @Bean
    public Cat cat() {
        return new Cat();
    }
}
