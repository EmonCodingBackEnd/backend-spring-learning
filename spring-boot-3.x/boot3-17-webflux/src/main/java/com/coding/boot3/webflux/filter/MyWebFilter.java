package com.coding.boot3.webflux.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class MyWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        System.out.println("请求处理放行到目标方法之前...");
        Mono<Void> voidMono = chain.filter(exchange)
                .doOnError(throwable -> {
                    System.out.println("请求处理过程中出现异常...");
                })
                .doFinally(signalType -> {
                    System.out.println("请求处理放行到目标方法之后...");
                });

        return voidMono;
    }
}
