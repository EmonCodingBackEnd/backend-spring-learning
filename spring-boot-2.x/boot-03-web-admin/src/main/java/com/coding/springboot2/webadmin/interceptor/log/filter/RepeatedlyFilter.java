package com.coding.springboot2.webadmin.interceptor.log.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.coding.springboot2.webadmin.interceptor.log.wrapper.RepeatedlyRequestWrapper;
import com.coding.springboot2.webadmin.interceptor.log.wrapper.RepeatedlyResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RepeatedlyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        RepeatedlyRequestWrapper requestWrapper = new RepeatedlyRequestWrapper(request);
        RepeatedlyResponseWrapper responseWrapper = new RepeatedlyResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
    }

}
