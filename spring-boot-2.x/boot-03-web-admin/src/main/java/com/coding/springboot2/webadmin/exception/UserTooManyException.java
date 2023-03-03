package com.coding.springboot2.webadmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "用户数量太多")
public class UserTooManyException extends RuntimeException {

    private static final long serialVersionUID = 3944799687086827653L;

    public UserTooManyException() {}

    public UserTooManyException(String message) {
        super(message);
    }
}
