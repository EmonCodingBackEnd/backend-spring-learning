package com.coding.spring.pojo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOCByXmlTest {

    /*
     * 获取 bean 的三种方式
     * 1、根据bean的id获取
     * 2、根据bean的类型获取【最常用】
     * 注意：根据类型获取 bean 时，要求IOC容器中有且只有一个类型匹配的bean。
     * 若没有任何一个类型匹配的bean，此时抛出异常 org.springframework.beans.factory.NoSuchBeanDefinitionException
     * 若有多个类型匹配的bean，此时抛出异常 org.springframework.beans.factory.NoUniqueBeanDefinitionException
     * 3、根据bean的id和类型
     *
     * 结论：
     * 根据类型来获取 bean 时，在满足 bean 唯一性的前提下，其实只是看：【对象 instanceof 指定的类型】的返回结果，
     * 只要返回结果是true，就可以认为和类型匹配，能够获取到。
     * 即通过 bean 的类型， bean 所继承的类型、 bean 所实现的接口的类型都可以获取 bean。
     *
     */
    @Test
    public void testGetBeanByName() {
        // 获取IOC容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-ioc.xml");
        // 获取bean
        Student studentOne = (Student)ioc.getBean("studentOne");
        System.out.println(studentOne);
    }

    @Test
    public void testGetBeanByType() {
        // 获取IOC容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-ioc.xml");
        // 获取bean
        Student studentOne = ioc.getBean(Student.class);
        System.out.println(studentOne);
    }

    @Test
    public void testGetBeanBySuperType() {
        // 获取IOC容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-ioc.xml");
        // 获取bean
        Person studentOne = ioc.getBean(Person.class);
        System.out.println(studentOne);
    }

    @Test
    public void testGetBeanByNameAndType() {
        // 获取IOC容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-ioc.xml");
        // 获取bean
        Student studentOne = ioc.getBean("studentOne", Student.class);
        System.out.println(studentOne);
    }
}