package com.coding.boot3.redis.controller;

import com.coding.boot3.redis.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class RedisTestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 默认情况下，RedisTemplate会自动使用JDK的序列化方式来序列化和反序列化value值，导致redis中保存的对象不可视。
     * 为了后来系统的兼容性，应该所有对象都是以json的方式进行保存。
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @GetMapping("/count")
    public String count() {
        Long count = stringRedisTemplate.opsForValue().increment("count");

        /*
        常见数据类型：k: v value可以有很多类型
        string: 普通字符串   redisTemplate.opsForValue().set("key", "value");
        list:   列表        redisTemplate.opsForList().leftPush("list", "value");
        set:    集合        redisTemplate.opsForSet().add("set", "value");
        zset:   有序集合     redisTemplate.opsForZSet().add("zset", "value", 1);
        hash:   散列        redisTemplate.opsForHash().put("hash", "key", "value");
         */

        return "访问了【" + count + "】次！";
    }


    @GetMapping("/person/save")
    public String savePerson() {
        Person person = new Person(1L, "张三", 18, new Date());
        redisTemplate.opsForValue().set("person", person);
        return "保存成功！";
    }


    @GetMapping("/person/get")
    public Person getPerson() {
        Person person = (Person) redisTemplate.opsForValue().get("person");
        return person;
    }
}
