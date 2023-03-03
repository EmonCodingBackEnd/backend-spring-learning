package com.coding.springboot2.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.coding.springboot2.web.bean.Person;

@RestController
public class ParameterController {

    @RequestMapping("/car/{id}/owner/{username}")
    public Map<String, Object> hello(@PathVariable("id") Integer id, @PathVariable("username") String name,
        @PathVariable Map<String, String> pv, @RequestHeader("User-Agent") String userAgent,
        @RequestHeader Map<String, String> header, @RequestParam("age") Integer age,
        @RequestParam("inters") List<String> inters, @RequestParam MultiValueMap<String, String> params,
        @CookieValue("Webstorm-6185e2e0") String webstorm, @CookieValue("Webstorm-6185e2e0") Cookie cookie,
        @RequestBody String content) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("pv", pv);
        map.put("userAgent", userAgent);
        map.put("header", header);
        map.put("age", age);
        map.put("inters", inters);
        map.put("params", params);
        map.put("webstorm", webstorm);
        map.put("cookie", cookie);
        map.put("content", content);
        return map;
    }

    /**
     * /car/sell;low=34;brand=byd,audi,yd
     *
     * SpringBoot默认禁用矩阵变量（我不这么认为，实际测试默认开启的），需要手动开启，原理是：UrlPathHelper解析时，默认会移除掉分号
     * 
     * 矩阵变量必须有url路径变量，才能被解析
     */
    @GetMapping("/cars/{path}")
    public Map<String, Object> carsSell(@MatrixVariable("low") Integer low, @MatrixVariable("brand") List<String> brand,
        @PathVariable("path") String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("low", low);
        map.put("brand", brand);
        map.put("path", path);
        return map;
    }

    // /boss/1;age=20/2;age=10
    @GetMapping("/boss/{bossId}/{empId}")
    public Map<String, Object> boss(@MatrixVariable(value = "age", pathVar = "bossId") Integer bossAge,
        @MatrixVariable(value = "age", pathVar = "empId") Integer empAge) {
        Map<String, Object> map = new HashMap<>();
        map.put("bossAge", bossAge);
        map.put("empAge", empAge);
        return map;
    }

    /**
     * 数据绑定：页面提交的请求（GET、POST）都可以和对象属性进行绑定
     * 
     * @param person
     * @return
     */
    @PostMapping("/saveuser")
    public Person saveuser(Person person) {
        return person;
    }

}
