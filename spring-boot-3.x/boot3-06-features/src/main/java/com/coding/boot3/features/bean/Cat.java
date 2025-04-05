package com.coding.boot3.features.bean;

import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//@Profile({"dev", "test"})
//@Component
@Data
public class Cat {
    private Long id;
    private String name;
    private Integer age;
}
