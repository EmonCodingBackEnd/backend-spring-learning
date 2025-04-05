package com.coding.boot3.webflux.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;

@Controller
public class RenderController {

    @GetMapping("/render")
    public Rendering render() {
//        Rendering.redirectTo("/aaa"); // 重定向到当前项目根路径下的 aaa
        return Rendering.redirectTo("https://www.baidu.com").build();
    }
}
