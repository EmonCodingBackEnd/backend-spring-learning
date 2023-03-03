package com.coding.springboot2.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @RequestMapping("/db01.jpeg")
    public String hello() {
        return "hello";
    }

    @GetMapping("/user")
    public String getUser() {
        return "GET-张三";
    }

    @PostMapping("/user")
    public String postUser() {
        return "POST-张三";
    }

    @PutMapping("/user")
    public String putUser() {
        return "PUT-张三";
    }

    @DeleteMapping("/user")
    public String deleteUser() {
        return "DELETE-张三";
    }

    @PatchMapping("/user")
    public String patchUser() {
        return "PATCH-张三";
    }
}
