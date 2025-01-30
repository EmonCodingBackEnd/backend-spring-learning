package com.coding.springboot3.demo.config.config;

//import com.alibaba.druid.FastsqlException;

import com.alibaba.druid.FastsqlException;
import com.coding.springboot3.demo.bean.Pig;
import com.coding.springboot3.demo.bean.Sheep;
import com.coding.springboot3.demo.bean.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

// 给容器添加指定类别的组件：方式二@Import
//@Import(FastsqlException.class) // 给容器中放指定类别的组件，组件的名字默认是全类名，效果和@Bean类似，用于导入第三方组件。
@Configuration // 这是一个配置类，替代以前的配置文件。配置类本身也是容器中的组件。
// @SpringBootConfiguration 与@Configuration等效
// 启用一个配置文件：方式三@ConfigurationProperties+@EnableConfigurationProperties(Sheep.class)
@EnableConfigurationProperties(Sheep.class) // 开启Sheep组件的属性绑定，并默认会把这个组件自己放到容器中；多用于导入第三方写好的组件进行属性绑定
public class AppConfig {

    /**
     * 1、组件默认是单实例的
     *
     * @return
     */
    @Scope("prototype")
    @Bean("userHahs") // 替代以前的Bean标签。组件在容器中的名字是方法名，也可以在Bean中配置（优先级高于方法名）
    public User user01() {
        var user = new User();
        user.setId(1L);
        user.setName("张三");
        return user;
    }

    // 给容器添加指定类别的组件：方式一@Import
    /*@Bean
    public FastsqlException fastsqlException() {
        return new FastsqlException();
    }*/

    // 启用一个配置文件：方式二@ConfigurationProperties+@Bean
    @Bean
    @ConfigurationProperties(prefix = "pig")
    public Pig pig() {
        return new Pig(); // 我们自己new新pig
    }

}
