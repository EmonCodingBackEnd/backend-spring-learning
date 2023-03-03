package com.coding.springboot2.helloworld.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "person.pet")
public class Pet {

    private String name;
    private Double weight;

}
