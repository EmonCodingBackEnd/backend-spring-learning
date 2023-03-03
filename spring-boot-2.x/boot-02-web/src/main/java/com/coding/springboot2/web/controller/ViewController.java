package com.coding.springboot2.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/emon")
    public String emon(Model model) {
        // model中的数据会被放在请求域中 request.setAttribute("a", aa)
        model.addAttribute("msg", "你好啊！");
        model.addAttribute("link", "http://www.baidu.com");
        return "success";
    }
}
