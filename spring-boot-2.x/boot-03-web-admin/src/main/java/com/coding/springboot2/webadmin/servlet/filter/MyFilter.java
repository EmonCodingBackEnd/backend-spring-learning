package com.coding.springboot2.webadmin.servlet.filter;

import java.io.IOException;

import javax.servlet.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @WebFilter(urlPatterns = {"/css/*", "/images/*"})
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter初始化完成");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        log.info("MyFilter doFilter");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("MyFilter销毁");
    }
}
