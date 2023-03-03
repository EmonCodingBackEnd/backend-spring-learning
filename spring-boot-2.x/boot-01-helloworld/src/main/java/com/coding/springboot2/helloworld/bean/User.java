package com.coding.springboot2.helloworld.bean;

import lombok.Data;

@Data
public class User {

    private String name;

    private Integer age;

    private Pet pet;
}