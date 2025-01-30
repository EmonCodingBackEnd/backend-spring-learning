package com.coding.springboot3.demo.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private int age;
    private Date birthday;
    private Boolean like;
    private Child child; // 嵌套对象
    private List<Dog> dogs; // 数字（里面是对象）
    private Map<String, Cat> cats; // 表示Map
}
