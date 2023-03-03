package com.coding.springboot2.webadmin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录检查
 *
 * 1、配置好拦截器要拦截哪些请求 2、把这些配置放到容器中
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 目标方法执行之前
     * 
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String requestURI = request.getRequestURI();
//        log.info("preHandle拦截的请求是：{}", requestURI);
        // 登录检查逻辑
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            // 放行
            return true;
        }

        // 拦截住
        session.setAttribute("msg", "请先登录！");
        response.sendRedirect("/");
        return false;
    }

    /**
     * 目标方法执行完成以后
     * 
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler the handler (or {@link HandlerMethod}) that started asynchronous execution, for type and/or
     *            instance examination
     * @param modelAndView the {@code ModelAndView} that the handler returned (can also be {@code null})
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
//        log.info("postHandle执行：{}", modelAndView);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 页面渲染以后
     * 
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler the handler (or {@link HandlerMethod}) that started asynchronous execution, for type and/or
     *            instance examination
     * @param ex any exception thrown on handler execution, if any; this does not include exceptions that have been
     *            handled through an exception resolver
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
//        log.info("afterCompletion执行：", ex);
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
