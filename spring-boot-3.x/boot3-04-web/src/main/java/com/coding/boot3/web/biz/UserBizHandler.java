package com.coding.boot3.web.biz;

import com.coding.boot3.web.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Arrays;
import java.util.List;

/**
 * 专门处理User相关的业务
 */
@Slf4j
@Service
public class UserBizHandler {

    /**
     * 查询单个用户
     *
     * @param request
     * @return
     * @throws Exception
     */
    public ServerResponse getUser(ServerRequest request) throws Exception {
        String id = request.pathVariable("id");
        log.info("获取id={}的用户信息", id);
        // 业务处理
        Person person = new Person(1L, "哈哈", "aa@qq.com", 18, "admin");
        // 构造响应
        return ServerResponse.ok().body(person);
    }

    /**
     * 获取所有用户
     *
     * @param request
     * @return
     * @throws Exception
     */
    public ServerResponse getUsers(ServerRequest request) throws Exception {
        // 业务处理
        List<Person> list = Arrays.asList(new Person(1L, "张三", "", 15, "pm"),
                new Person(2L, "李四", "ls@qq.com", 16, "pm"),
                new Person(3L, "王五", "ww@qq.com", 17, "manager"),
                new Person(4L, "赵六", "zl@qq.com", 18, "admin"),
                new Person(5L, "田七", "tq@qq.com", 19, "hr"));
        // 构造响应
        return ServerResponse.ok().body(list); // 凡是body中的对象，就是以前 @ResponseBody 原理。利用 HttpMessageConverter 写出为 json/xml/yaml 等
    }

    /**
     * 保存用户
     *
     * @param request
     * @return
     * @throws Exception
     */
    public ServerResponse saveUser(ServerRequest request) throws Exception {
        // 提取请求体
        Person person = request.body(Person.class);
        log.info("保存用户信息完成，person={}", person);
        return ServerResponse.ok().build();
    }

    /**
     * 更新用户
     *
     * @param request
     * @return
     * @throws Exception
     */
    public ServerResponse updateUser(ServerRequest request) throws Exception {
        log.info("更新用户信息完成");
        return ServerResponse.ok().build();
    }

    /**
     * 删除用户
     *
     * @param request
     * @return
     * @throws Exception
     */
    public ServerResponse deleteUser(ServerRequest request) throws Exception {
        log.info("删除用户信息完成");
        return ServerResponse.ok().build();
    }

}
