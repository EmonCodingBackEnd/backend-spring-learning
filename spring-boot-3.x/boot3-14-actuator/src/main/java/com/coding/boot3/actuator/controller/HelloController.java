package com.coding.boot3.actuator.controller;

import com.coding.boot3.actuator.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private MyService component;

    @GetMapping("/hello")
    public String hello() {
        // 业务调用
        component.hello();
        return "hello";
    }
}
