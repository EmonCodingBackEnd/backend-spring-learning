package com.coding.boot3.features;

import com.coding.boot3.features.bean.Cat;
import com.coding.boot3.features.bean.Dog;
import com.coding.boot3.features.bean.Pig;
import com.coding.boot3.features.bean.Sheep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;


/**
 * 1、标识环境
 * 1）、区分出几个环境：dev（开发环境）、test（测试环境）、prod（生产环境）
 * 2）、指定每个组件在哪个环境下生效；default（默认环境）
 * 通过： @Profile({"test"})
 * 组件没有标注@Profile代表任意时候都生效
 * 3）、默认只有激活指定的环境，这些组件才会生效。
 * 2、激活环境
 * 配置文件激活：spring.profiles.active=dev
 * 命令行激活：java -jar xxx.jar --spring.profiles.active=dev
 * 3、配置文件怎么使用profile功能
 * 1）、application.properties：主配置文件。任何情况下都生效
 * 2）、其他profile环境下命名规范：application-${profile}.properties；比如：application-dev.properties
 * 3）、激活指定环境即可：配置文件激活、命令行激活
 * 4）、效果：
 * 项目的所有生效配置项 = 激活环境配置文件的所有项 + 主配置文件中与激活配置文件不冲突的所有项
 * 如果发生了配置冲突，以激活的配置项为准
 * application-{profile}.properties 优先级高于  application.properties
 * 主配置文件和激活的配置都生效时，优先以激活的配置项为准。
 *
 * 注意： java -jar features.jar --spring.profiles.active=dev
 * 等效于 java -jar -Dspring.profiles.active=dev features.jar
 * 等效于 java -Dspring.profiles.active=dev -jar features.jar
 */
@Slf4j
@RestController
@SpringBootApplication
public class Boot306Application {

    public static void main(String[] args) {
        // 1、SpringApplication：Boot应用的核心API入口
//        SpringApplication.run(Boot306Application.class, args);

        /*// 1、自定义SpringApplication的底层设置
        SpringApplication application = new SpringApplication(Boot306Application.class);

        // 程序化调整SpringApplication的参数
        // 配置文件中的优先级，高于这里的调整优先级
        application.setBannerMode(Banner.Mode.CONSOLE);

        // 2、SpringApplication 运行起来
        application.run(args);*/

        // 2、Builder方式构建SpringApplication，通过FlentAPI进行设置
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(Boot306Application.class)
                .bannerMode(Banner.Mode.CONSOLE)
                .properties("server.port=8888", "aaa=bbb")
                .run(args);

        String aaaaa = context.getEnvironment().getProperty("aaaaa");
        System.out.println("aaaaa = " + aaaaa);
        String appdesc = context.getEnvironment().getProperty("app.description");
        System.out.println("appdesc = " + appdesc);

        try {
            Cat cat = context.getBean(Cat.class);
            System.out.println("cat = " + cat);
        } catch (BeansException ignored) {
        }

        try {
            Dog dog = context.getBean(Dog.class);
            System.out.println("dog = " + dog);
        } catch (BeansException ignored) {
        }

        try {
            Pig pig = context.getBean(Pig.class);
            System.out.println("pig = " + pig);
        } catch (BeansException ignored) {
        }

        try {
            Sheep sheep = context.getBean(Sheep.class);
            System.out.println("sheep = " + sheep);
            System.out.println();
        } catch (BeansException ignored) {
        }

    }

}