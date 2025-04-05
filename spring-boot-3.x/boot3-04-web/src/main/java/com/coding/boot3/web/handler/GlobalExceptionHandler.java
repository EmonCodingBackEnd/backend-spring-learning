package com.coding.boot3.web.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@ControllerAdvice // 这个类是集中处理所有 @Controller 发生的错误
public class GlobalExceptionHandler {


    /**
     * 1、@ExceptionHandler 标识一个方法处理错误，默认只能处理这个类发生的指定错误
     * 2、@ControllerAdvice 统一处理所有错误
     *
     * @param e
     * @param model
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        return "error~统一" + "原因：" + e.getMessage();
    }
}
