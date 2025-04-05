package com.coding.boot3.ssm.controller;

import com.coding.boot3.ssm.bean.TUser;
import com.coding.boot3.ssm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user/{id}")
    public TUser getUser(@PathVariable("id") Long id) {
        TUser user = userMapper.getUserById(id);
        return user;
    }
}
