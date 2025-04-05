package com.coding.boot3.features.bean;

import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//@Profile("default")
@Component
@Data
public class Sheep {
    private Long id;
    private String name;
    private Integer age;
}
