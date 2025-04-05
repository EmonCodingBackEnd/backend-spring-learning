package com.coding.boot3.web.controller;

import com.coding.boot3.web.bean.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
public class HelloController {

    /**
     * 默认使用新版 PathPatternParser 进行路径匹配
     * 不能匹配 ** 在中间的情况，剩下的和 AntPathMatcher 语法兼容
     *
     * @param request
     * @param p1
     * @return
     */
    @GetMapping("/a*/b?/{p1:[a-f]+}/**")
    public String hello(HttpServletRequest request, @PathVariable("p1") String p1) {
        /*
         * http://localhost:9000/alibaba/ba/abcf/a/b/c
         * 路径变量p1:abcf
         */
        log.info("路径变量p1:{}", p1);
        String uri = request.getRequestURI();
        return uri;
    }

    /**
     * 1、默认支持把对象写为json，因为默认web场景导入了jackson处理json的包；jackson-core
     *
     * @return
     */
    @GetMapping("/person")
    public Person person() {
        Person person = new Person();
        person.setId(1L);
        person.setUserName("张三");
        person.setEmail("aaa@qq.com");
        person.setAge(18);
        return person;
    }

    // 国际化取消息用的组件
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/i18n")
    public String haha(HttpServletRequest request) {
        Locale locale = request.getLocale();
        // 利用代码的方式获取国际化配置文件中指定的配置项的值
        String login = messageSource.getMessage("login", null, locale);
        return login;
    }

    @GetMapping("/error2")
    public String error2(Model model) {
        int total = 10 / 0;
        return "list";
    }

    public static void main(String[] args) throws JsonProcessingException {

        Person person = new Person();
        person.setId(1L);
        person.setUserName("张三");
        person.setEmail("aaa@qq.com");
        person.setAge(18);

        YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(factory);

        String s = mapper.writeValueAsString(person);
        System.out.println(s);
    }
}
