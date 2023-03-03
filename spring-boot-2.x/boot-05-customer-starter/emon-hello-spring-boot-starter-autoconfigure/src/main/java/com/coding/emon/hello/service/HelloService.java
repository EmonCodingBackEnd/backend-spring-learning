package com.coding.emon.hello.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.coding.emon.hello.HelloProperties;

public class HelloService {

    @Autowired
    HelloProperties helloProperties;

    public String sayHello(String userName) {
        return helloProperties.getPrefix() + ":" + userName + ">>" + helloProperties.getSuffix();
    }
}
