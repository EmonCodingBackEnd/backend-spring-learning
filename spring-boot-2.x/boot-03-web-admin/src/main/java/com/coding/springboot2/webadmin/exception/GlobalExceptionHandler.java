package com.coding.springboot2.webadmin.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 处理整个web的异常
 * 
 * 优先级，比@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "用户数量太多")高
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ArithmeticException.class})
    public String handlerException(Exception e) {
        log.error("异常是：", e);
        return "login";
    }
}
