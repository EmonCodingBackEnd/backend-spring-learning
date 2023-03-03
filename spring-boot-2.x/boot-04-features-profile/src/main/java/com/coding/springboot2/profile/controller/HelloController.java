package com.coding.springboot2.profile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.springboot2.profile.bean.Person;

@RestController
public class HelloController {

    @Value("${person.name:李四}")
    private String name;

    @GetMapping("/")
    public String hello() {
        return "Hello " + name;
    }

    @Autowired
    private Person person;

    @GetMapping("/person")
    public Person person() {
        return person;
    }

    @Value("${M2_HOME}")
    private String maven;

    @GetMapping("/m2")
    public String m2() {
        return maven;
    }
}
