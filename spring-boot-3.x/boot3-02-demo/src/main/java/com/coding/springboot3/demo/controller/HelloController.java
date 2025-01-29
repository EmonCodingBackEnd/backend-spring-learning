package com.coding.springboot3.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/hello")
    public String hello() {
        return "Hello,Spring Boot 3!";
    }

    @GetMapping("/incr")
    public String incr() {
        Long incr = redisTemplate.opsForValue().increment("incr");
        return "增加后的值：" + incr;
    }
}
