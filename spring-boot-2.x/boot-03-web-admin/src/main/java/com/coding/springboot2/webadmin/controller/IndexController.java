package com.coding.springboot2.webadmin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coding.springboot2.webadmin.bean.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {

    /**
     * 访问登录页
     */
    @GetMapping(value = {"/", "/login"})
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String main(User user, HttpSession httpSession, Model model) {
        log.info("/login");
        if (StringUtils.hasText(user.getUserName()) && StringUtils.hasText(user.getPassword())
            && "123456".equals(user.getPassword())) {
            httpSession.setAttribute("loginUser", user);
            // 登录成功，重定向到main.html
            return "redirect:/main.html";
        } else {
            // 回到登录页
            model.addAttribute("msg", "账号密码错误！");
            return "login";
        }
    }

    @GetMapping("/main.html")
    public String mainPage(HttpSession httpSession, Model model) throws Exception {
        log.info("/main.html");
        Object loginUser = httpSession.getAttribute("loginUser");
        if (loginUser != null) {
            return "main";
        } else {
            model.addAttribute("msg", "请重新登录");
            return "login";
        }
    }
}
