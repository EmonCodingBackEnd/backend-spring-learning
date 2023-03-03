package com.coding.springboot2.webadmin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String userName;
    private Integer age;
    private String email;
    private String password;
}
