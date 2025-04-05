package com.coding.boot3.logging.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

//    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/hello")
    public String hello() {
//        logger.info("Logger=>hello");
//        log.info("lombok=>hello");
        for (int i = 0; i < 100000; i++) {
            log.info("增加日志内容");
        }
        log.trace("trace");
        log.debug("debug");
        // SpringBoot底层默认的日志级别是 info
        log.info("info");
        log.warn("warn");
        log.error("error");
        return "hello";
    }
}
