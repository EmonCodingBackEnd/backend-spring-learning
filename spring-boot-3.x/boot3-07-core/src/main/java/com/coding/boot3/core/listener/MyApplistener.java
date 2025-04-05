package com.coding.boot3.core.listener;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;

/**
 * SpringBoot应用生命周期监听
 * Listener先要从 META-INF/spring.factories 读到
 * 1、引导：利用 BootstrapContext 引导整个项目启动
 * starting：应用开始，SpringApplication的run方法一调用，只要有了 BootstrapContext 就执行
 * environmentPrepared：环境准备好（把启动参数等绑定到环境变量中），但是IOC还没有创建【调一次】
 * 2、启动：
 * contextPrepared：IOC容器创建并准备好，但是sources（主配置类）尚未加载，并关闭引导上下文；组件并未创建。【调一次】
 * contextLoaded： IOC容器已经被加载。主配置加载进去了，但是IOC容器还没刷新（我们的bean没有创建）。
 * ==========截止以前，ioc容器里面还没造bean呢==========
 * started：IOC容器刷新了（所有bean造好了），但是 runner 没调用。
 * ready：IOC容器刷新了（所有bean造好了），所有 runner 调用完了
 * 3、运行
 * 如果以前步骤都正确执行，代表容器 running
 */
public class MyApplistener implements SpringApplicationRunListener, Ordered {

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        System.out.println("==========starting=====正在启动==========");
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        System.out.println("==========environmentPrepared=====环境准备完成==========");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("==========contextPrepared=====IOC容器准备完成==========");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("==========contextLoaded=====IOC容器加载完成==========");
    }

    @Override
    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        System.out.println("==========started=====启动完成==========");
    }

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        System.out.println("==========ready=====准备就绪==========");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("==========exception=====应用异常==========");
    }
}
