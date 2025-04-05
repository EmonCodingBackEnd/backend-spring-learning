package com.coding.boot3.features.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @Value("${name:名字不存在}")
    String name;

    @GetMapping("hello")
    public String hello() {
        return name;
    }
}
