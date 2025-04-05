package com.coding.boot3.web.controller;

import com.coding.boot3.web.bean.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller // 适配 服务端渲染，前后不分离模式开始
public class WelcomeController {

    @GetMapping("/well")
    public String hello(@RequestParam(value = "name", defaultValue = "陌生人") String name, Model model) {
        // 模板的逻辑视图
        // 物理视图 = 前缀 + 逻辑视图
        // 真实地址 = classpath:/templates/welcome.html

        // 把需要给页面共享的数据放到 model 中
        String msg = "<span style='color:red'>" + name + "</span>";
        model.addAttribute("name", name);
        model.addAttribute("msg", msg);
        // 路径是动态的
        model.addAttribute("imgUrl", "2.png");
        // 模拟数据库查出的样式
        model.addAttribute("style", "width: 400px;");
        model.addAttribute("show", true);
        return "welcome";
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/list")
    public String hello(Model model) {
        List<Person> list = Arrays.asList(new Person(1L, "张三", "", 15, "pm"),
                new Person(2L, "李四", "ls@qq.com", 16, "pm"),
                new Person(3L, "王五", "ww@qq.com", 17, "manager"),
                new Person(4L, "赵六", "zl@qq.com", 18, "admin"),
                new Person(5L, "田七", "tq@qq.com", 19, "hr"));
        model.addAttribute("persons", list);
        return "list";
    }

    @GetMapping("/error1")
    public String error1(Model model) {
        int total = 10 / 0;
        return "list";
    }

    /**
     * 1、@ExceptionHandler 标识一个方法处理错误，默认只能处理这个类发生的指定错误
     * 2、@ControllerAdvice 统一处理所有错误
     *
     * @param e
     * @param model
     * @return
     */
    @ResponseBody
//    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        return "error" + "原因：" + e.getMessage();
    }

}

