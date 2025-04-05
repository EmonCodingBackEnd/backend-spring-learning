package com.coding.boot3.webflux.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ArithmeticException.class)
    public String handleException(ArithmeticException e) {
        System.out.println("GlobalExceptionHandler.handleException 发生了数学运算异常" + e);
        // 返回这些进行错误处理
//        ProblemDetail
//        ErrorResponse
        return "炸了，哈哈";
    }
}
