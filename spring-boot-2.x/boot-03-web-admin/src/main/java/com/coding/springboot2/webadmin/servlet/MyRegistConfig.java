package com.coding.springboot2.webadmin.servlet;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coding.springboot2.webadmin.servlet.filter.MyFilter;
import com.coding.springboot2.webadmin.servlet.listener.MyServletContextListener;

@Configuration
public class MyRegistConfig {

    @Bean
    public ServletRegistrationBean<MyServlet> myServlet() {
        MyServlet myServlet = new MyServlet();
        return new ServletRegistrationBean<>(myServlet, "/my", "/my02");
    }

    @Bean
    public FilterRegistrationBean<MyFilter> myFilter() {
        /*MyFilter myFilter = new MyFilter();
        return new FilterRegistrationBean<>(myFilter, myServlet());*/
        MyFilter myFilter = new MyFilter();
        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>(myFilter);
        registrationBean.setUrlPatterns(Arrays.asList("/my", "/css/*"));
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<MyServletContextListener> myListener() {
        MyServletContextListener myListener = new MyServletContextListener();
        return new ServletListenerRegistrationBean<>(myListener);

    }
}
