package com.coding.spring6.first;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;

@Log4j2
// @ContextConfiguration(locations = {"classpath:bean.xml"})
class UserTest {

    @Test
    void testUserBean() {
        // 加载Spring配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");

        // 获取创建的对象
        User user = (User) context.getBean("user");
        System.out.println(user);

        // 使用对象调用方法进行测试
        user.add();
        log.info("执行调用成功！");
    }

    @Test
    void testUserReflect() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 获取类Class对象
        Class<?> clazz = Class.forName("com.coding.spring6.first.User");

        // 创建对象
        User user = (User) clazz.getDeclaredConstructor().newInstance();
        System.out.println(user);

        // 调用
        user.add();
    }
}