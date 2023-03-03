package com.coding.springboot2.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 是主程序类；主配置类 <br/>
 * 
 * @SpringBootApplication： 这是一个SpringBoot应用
 */
@SpringBootApplication
public class Boot01Application {

    public static void main(String[] args) {
        System.out.println("MainApplication");
        // 1、返回我们IOIC容器
        ConfigurableApplicationContext run = SpringApplication.run(Boot01Application.class);
        // 2、查看容器里面的组件
        // String[] names = run.getBeanDefinitionNames();
        // for (String name : names) {
        // System.out.println(name);
        // }

        // // 3、从容器中获取组件
        // Pet tom01 = run.getBean("tom", Pet.class);
        // Pet tom02 = run.getBean("tom", Pet.class);
        // System.out.println(tom01 == tom02);
        //
        // // 4、如果 @Configuration(proxyBeanMethods = true) 代理对象调用方法。SpringBoot总会检查这个组件是否存在于容器中了，保持组件单实例。
        // MyConfig myConfig = run.getBean(MyConfig.class);
        // System.out.println(myConfig);
        //
        // User user01 = myConfig.user01();
        // User user02 = myConfig.user01();
        // System.out.println(user01 == user02);
        //
        // User user = run.getBean("user01", User.class);
        // Pet tom = run.getBean("tom", Pet.class);
        // System.out.println(user.getPet() == tom);
        //
        // // 5、获取组件
        // String[] beanNamesForTypes = run.getBeanNamesForType(User.class);
        // System.out.println("==========");
        // for (String beanNamesForType : beanNamesForTypes) {
        // System.out.println(beanNamesForType);
        // }
        //
        // Transform transform = run.getBean(Transform.class);
        // System.out.println(transform);

        System.out.println(run.containsBean("tom"));
        System.out.println(run.containsBean("user01"));

        System.out.println(run.containsBean("userOfXml"));
        System.out.println(run.containsBean("catOfXml"));
    }

}
