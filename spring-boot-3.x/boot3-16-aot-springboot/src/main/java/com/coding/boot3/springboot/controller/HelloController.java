package com.coding.boot3.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {
    @GetMapping("/")
    public String hello() {
        log.info("Hello Spring Boot 3.x! 你好！");
        return "Hello Spring Boot 3.x! 你好！";
    }
}
