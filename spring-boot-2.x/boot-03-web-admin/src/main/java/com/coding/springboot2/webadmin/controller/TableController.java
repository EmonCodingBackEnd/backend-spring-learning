package com.coding.springboot2.webadmin.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coding.springboot2.webadmin.bean.User;
import com.coding.springboot2.webadmin.exception.UserTooManyException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TableController {

    @GetMapping(value = {"/basic_table"})
    public String basicTable() {
        int i = 10 / 0;
        return "table/basic_table";
    }

    @ResponseBody
    @GetMapping(value = {"/basic_table2"})
    public User basicTable2() {
        int i = 10 / 0;
        return new User("1", "lm", 10, "lm@qq.com", "123456");
    }

    @GetMapping(value = {"/dynamic_table"})
    public String dynamicTable(Model model) {
        // 表格内容的遍历
        List<User> users = Arrays.asList(new User("1", "zhangsan", 18, "zhangsan@qq.com", "123456"),
            new User("2", "lisi", 18, "lisi@qq.com", "123456"), new User("3", "haha", 18, "haha@qq.com", "123456"));
        Map<String, Object> map = new HashMap<>();
        map.put("records", users);
        map.put("current", 1);
        map.put("pages", 3);
        map.put("total", 10);
        model.addAttribute("users", map);

        if (users.size() > 2) {
            throw new UserTooManyException();
        }
        return "table/dynamic_table";
    }

    @GetMapping(value = {"/responsive_table"})
    public String responsiveTable() {
        return "table/responsive_table";
    }

    @GetMapping(value = {"/editable_table"})
    public String editableTable() {
        return "table/editable_table";
    }
}