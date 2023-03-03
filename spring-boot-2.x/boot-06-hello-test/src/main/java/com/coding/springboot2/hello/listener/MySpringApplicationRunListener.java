package com.coding.springboot2.hello.listener;

import java.time.Duration;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.env.ConfigurableEnvironment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    private final SpringApplication application;

    private final String[] args;

    private final SimpleApplicationEventMulticaster initialMulticaster;

    public MySpringApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
        this.initialMulticaster = new SimpleApplicationEventMulticaster();
        for (ApplicationListener<?> listener : application.getListeners()) {
            this.initialMulticaster.addApplicationListener(listener);
        }
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        // 注意，这里还无法打印出log.info
        System.err.println("1==>MySpringApplicationRunListener ...starting...");
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext,
        ConfigurableEnvironment environment) {
        System.err.println("2==>MySpringApplicationRunListener ...environmentPrepared...");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.err.println("4==>MySpringApplicationRunListener ...contextPrepared...");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.err.println("5==>MySpringApplicationRunListener ...contextLoaded...");
    }

    @Override
    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        System.err.printf("6==>MySpringApplicationRunListener ...started...cost=%ss\n", timeTaken.getSeconds());
    }

    /*@Override
    public void started(ConfigurableApplicationContext context) {
        System.err.println("==>MySpringApplicationRunListener ...started...");
    }*/

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        System.err.println("9==>MySpringApplicationRunListener ...ready...");
    }

    /*@Override
    public void running(ConfigurableApplicationContext context) {
        System.err.println("==>MySpringApplicationRunListener ...running...");
    }*/

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.err.println("==>MySpringApplicationRunListener ...failed...");
    }
}
