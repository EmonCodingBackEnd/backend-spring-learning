package com.coding.boot3.web.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement // 可以写出为xml文档
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Long id;
    private String userName;
    private String email;
    private int age;
    private String role;
}
