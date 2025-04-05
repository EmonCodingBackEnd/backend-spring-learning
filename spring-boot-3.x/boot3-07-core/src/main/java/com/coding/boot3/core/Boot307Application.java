package com.coding.boot3.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@EnableWebMvc
//@EnableAsync
//@EnableScheduling
@SpringBootApplication
public class Boot307Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot307Application.class, args);
    }

}