package com.coding.boot3.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private Long id;
    private String name;
    private int age;
    private Date birthDay;
}
