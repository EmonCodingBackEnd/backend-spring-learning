package com.coding.boot3.features.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public int sum(int a, int b) {
        return a + b;
    }
}
