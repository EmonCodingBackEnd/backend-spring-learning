package com.coding.springboot2.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.emon.hello.service.HelloService;

@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;

    @GetMapping("/")
    public String sayHello(String userName) {
        return helloService.sayHello(userName);
    }
}
