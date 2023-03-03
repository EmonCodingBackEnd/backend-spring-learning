package com.coding.springboot2.web.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coding.springboot2.web.bean.Person;

@Controller
public class ResponseController {

    /**
     * 请求头： Accept ==> application/xml 会返回xml数据
     * 
     * Accept ==> application/json 会返回json数据
     *
     * 自定义converter：<br/>
     * 1、浏览器发送请求返回xml [application/xml] ==> jacksonXmlConverter<br/>
     * 2、如果是ajax请求，返回json [appplication/json] ==> jacksonJsonConverter<br/>
     * 3、如果是emon发送请求，返回自定义协议数据[application/emon] xxxConvertere<br/>
     * 
     * 步骤：<br/>
     * 1、添加自定义的MessageConverter进入系统底层<br/>
     * 2、系统底层会统计出所有MessageConverter能操作哪些类型<br/>
     * 3、客户端内存协商
     * 
     * Accept ==> application/json;q=0.4,application/xml;q=0.6,application/emon;q=0.8
     */
    @ResponseBody
    @GetMapping("/test/person")
    public Person getPerson() {
        Person person = new Person();
        person.setAge(28);
        person.setBirth(new Date());
        person.setUserName("zhangsan");
        return person;
    }
}
