package com.coding.springboot3.demo.config.config;

import com.coding.springboot3.demo.bean.Cat;
import com.coding.springboot3.demo.bean.Dog;
import com.coding.springboot3.demo.bean.User;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;

@ConditionalOnMissingClass(value = "com.alibaba.druid.FastsqlException") // 放在类级别，如果注解判断生效，则整个配置类才生效
@SpringBootConfiguration
public class AppConfig2 {
    @ConditionalOnClass(name = "com.alibaba.druid.FastsqlException") // 放在方法级别，单独对这个方法进行注解判断。
    @Bean
    public Cat cat01() {
        return new Cat();
    }

    @ConditionalOnMissingClass(value = "com.alibaba.druid.FastsqlException")
    @Bean
    public Dog dog01() {
        return new Dog();
    }

    @ConditionalOnBean(name = "dog01")
    @Bean
    public User zhangsan() {
        return new User();
    }

    @ConditionalOnMissingBean(name = "dog01")
    @Bean
    public User lisi() {
        return new User();
    }
}
