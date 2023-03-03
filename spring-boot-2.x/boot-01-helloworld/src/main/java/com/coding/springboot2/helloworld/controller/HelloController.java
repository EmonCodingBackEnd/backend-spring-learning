package com.coding.springboot2.helloworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.springboot2.helloworld.bean.Car;
import com.coding.springboot2.helloworld.bean.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String handler01() {
        log.info("Hello, Spring Boot2.x!" + "你好！");
        return "Hello, Spring Boot2.x!" + "你好！";
    }

    @Autowired
    private Car car;

    @RequestMapping("/car")
    public Car car() {
        return car;
    }

    @Autowired
    private Person person;

    @RequestMapping("/person")
    public Person person() {
        return person;
    }
}
