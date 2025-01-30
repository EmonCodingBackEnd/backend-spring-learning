package com.coding.springboot3.demo;

import com.coding.springboot3.demo.bean.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Boot302Application {

    public static void main(String[] args) {
        // java10: 局部变量类型的自动推断
        var ioc = SpringApplication.run(Boot302Application.class, args);

        // 1 获取容器中所有组件的名字
        var names = ioc.getBeanDefinitionNames();
        // 2 挨个遍历
        // SpringBoot把以前配置的核心组件现在都给我们自动配置好了，比如：dispatcherServlet、beanNameViewResolver、characterEncodingFilter、multipartResolver
        /*for (String name : names) {
            System.out.println(name);
        }*/

        // 测试User是否单例组件
         /*System.out.println(ioc.getBean(User.class));
         System.out.println(ioc.getBean(User.class));*/

        // 测试@ConditionalOnXXX
        /*for (String cat : ioc.getBeanNamesForType(Cat.class)) {
            System.out.println("cat:" + cat);
        }

        for (String dog : ioc.getBeanNamesForType(Dog.class)) {
            System.out.println("dog:" + dog);
        }

        for (String user : ioc.getBeanNamesForType(User.class)) {
            System.out.println("user:" + user);
        }*/

        // 测试属性绑定
        /*Pig pig = ioc.getBean(Pig.class);
        System.out.println(pig);

        Sheep sheep = ioc.getBean(Sheep.class);
        System.out.println(sheep);*/

        // 测试YAML格式配置文件
        Person person = ioc.getBean(Person.class);
        System.out.println(person);
        System.out.println("=== 用|表示的大文本：");
        System.out.println(person.getChild().getText().get(2));
        System.out.println("=== 用>表示的大文本：");
        System.out.println(person.getChild().getText().get(3));
    }

}
