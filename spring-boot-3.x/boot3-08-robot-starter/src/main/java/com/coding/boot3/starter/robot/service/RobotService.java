package com.coding.boot3.starter.robot.service;

import com.coding.boot3.starter.robot.properties.RobotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RobotService {

    @Autowired
    private RobotProperties robotProperties;

    public String sayHello() {
        return "你好：名字：【" + robotProperties.getName() + "】，年龄：【" + robotProperties.getAge() + "】，邮箱：【" + robotProperties.getEmail() + "】";
    }
}
