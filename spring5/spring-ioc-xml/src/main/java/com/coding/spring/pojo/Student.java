package com.coding.spring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Person {

    private Integer sid;

    private String sname;

    private Integer age;

    private String gender;

}
