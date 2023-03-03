package com.coding.springboot2.webadmin;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class Boot03ApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from t_emp");
        for (Map<String, Object> map : maps) {
            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
                System.out.print(stringObjectEntry.getKey() + "=" + stringObjectEntry.getValue());
            }
            System.out.println();
        }
    }

}
