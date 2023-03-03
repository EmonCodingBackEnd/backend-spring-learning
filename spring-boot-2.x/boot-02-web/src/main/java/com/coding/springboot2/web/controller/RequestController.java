package com.coding.springboot2.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RequestController {

    @GetMapping("/goto")
    public String gotoPage(HttpServletRequest request) {
        request.setAttribute("msg", "成功了...");
        request.setAttribute("code", 200);
        return "forward:/success"; // 转发到 /success
    }

    @GetMapping("/params")
    public String testParam(Map<String, Object> map, Model model, HttpServletRequest request,
        HttpServletResponse response) {
        request.setAttribute("message", "HelloWorld");
        request.setAttribute("hello", "hello777");
        request.setAttribute("world", "world777");
        // 说明：给map、model和request放数据，都等效于request.setAttribute，优先级model > map > request.setAttribute
        map.put("hello", "hello555");
        map.put("world", "world555"); // 选中
        model.addAttribute("hello", "hello666"); // 选中 // 会覆盖map的相同key值
        model.addAttribute("message", "HelloWorld666"); // 选中

        Cookie cookie = new Cookie("c1", "v1");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
        return "forward:/success";
    }

    @ResponseBody
    @GetMapping("/success")
    public Map<String, Object> success(@RequestAttribute(value = "msg", required = false) String msg,
        @RequestAttribute(value = "code", required = false) Integer code, HttpServletRequest request) {
        System.out.println("code=" + request.getAttribute("code") + ",msg=" + request.getAttribute("msg"));
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);

        Object hello = request.getAttribute("hello");
        Object world = request.getAttribute("world");
        Object message = request.getAttribute("message");
        map.put("hello", hello);
        map.put("world", world);
        map.put("message", message);
        return map;
    }

}
