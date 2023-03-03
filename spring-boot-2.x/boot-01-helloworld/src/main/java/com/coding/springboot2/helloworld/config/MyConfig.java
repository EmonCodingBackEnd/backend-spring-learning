package com.coding.springboot2.helloworld.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.coding.springboot2.helloworld.bean.Car;
import com.coding.springboot2.helloworld.bean.Pet;
import com.coding.springboot2.helloworld.bean.User;

import ch.qos.logback.core.helpers.Transform;

/**
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * 
 * 2、配置类本身也是组件
 *
 * 3、proxyBeanMethods：代理bean的方法<br/>
 * Full(proxyBeanMethods = true)<br/>
 * Lite(proxyBeanMethods = false)<br/>
 *
 * 4、@Import({User.class, CyclicBuffer.class})<br/>
 * 给容器中自欧东创建出这两个类型的组件、默认组件的名字就是全类名。
 */
@Import({User.class, Transform.class})
// 告诉SpringBoot这个是配置类 == 配置文件(beans.xml)
@ImportResource("classpath:beans.xml")
@ConditionalOnMissingBean(name = {"userOfXml", "catOfXml"}) // 上面的@ImportResource产生的并不会影响到该判断
@Configuration(proxyBeanMethods = true)
@EnableConfigurationProperties(Car.class)
public class MyConfig {

    @Bean("tom")
    public Pet cat() {
        Pet pet = new Pet();
        pet.setName("tomcat");
        return pet;
    }

    /**
     * 外部无论对配置类中的这个组件注册方法调用了多少次，获取的都是之前注册容器中的单实例对象。
     * 
     * @return
     */
    // 给容器中添加组件。以方法名作为组件的id。返回类型就是组件的类型。返回的值，就是组件在容器中的实例。
    @ConditionalOnBean(name = "tom") // 因为依赖了tom这个bean，所以要先尝试加载tom，之后才能依赖tom是否存在
    @Bean
    public User user01() {
        User user = new User();
        user.setName("user01");
        user.setAge(18);
        user.setPet(cat());
        return user;
    }
}
