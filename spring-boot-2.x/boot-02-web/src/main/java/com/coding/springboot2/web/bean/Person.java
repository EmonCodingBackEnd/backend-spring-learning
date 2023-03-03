package com.coding.springboot2.web.bean;

import java.util.Date;

import lombok.Data;

@Data
public class Person {

    private String userName;
    private Boolean boss;
    private Date birth;
    private Integer age;
    private Pet pet;

}
