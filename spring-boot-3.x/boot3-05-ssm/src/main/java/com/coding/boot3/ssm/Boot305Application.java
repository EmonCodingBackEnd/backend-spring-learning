package com.coding.boot3.ssm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1、告诉MyBatis，扫描哪个包下面的所有接口
 * 2、告诉MyBatis，每个接口的xml文件位置
 * 3、MyBatis自动关联绑定
 */
@MapperScan(basePackages = {"com.coding.boot3.ssm.mapper"})
@RestController
@SpringBootApplication
public class Boot305Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot305Application.class, args);
    }

}