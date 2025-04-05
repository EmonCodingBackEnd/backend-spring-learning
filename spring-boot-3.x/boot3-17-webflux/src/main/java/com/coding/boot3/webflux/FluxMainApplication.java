package com.coding.boot3.webflux;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

public class FluxMainApplication {
    public static void main(String[] args) {
        // 快速自己编写一个能处理请求的服务器
        // 1、创建一个能处理 http 请求的处理器；参数：请求、响应；返回值：Mono<Void>：代表处理完成的信号
        HttpHandler httpHandler = (ServerHttpRequest request, ServerHttpResponse response) -> {
            // 编写请求处理的业务
            System.out.println(Thread.currentThread() + " 请求路径：" + request.getURI());
//            response.getHeaders(); // 获取响应头
//            response.getCookies(); // 获取Cookie
//            response.getStatusCode(); // 获取响应状态码
//            response.bufferFactory(); // buffer工厂
//            // response.writeWith(null); // 把XXX写出去
//            response.setComplete(); // 响应完成

            // 数据的发布者：Mono<DataBuffer>、Flux<DataBuffer>
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            // 创建一个数据缓冲区
            DataBuffer dataBuffer = dataBufferFactory.wrap("Hello, Spring Boot 3.0".getBytes());
            // 创建一个响应体，把数据缓冲区写入响应中
            Mono<DataBuffer> mono = Mono.just(dataBuffer);
            // 需要一个 DataBuffer 的发布者
            return response.writeWith(mono);
        };

        // 2、启动一个服务器，监听8080端口，接收数据，拿到数据交给 HttpHandler 进行请求处理
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);

        // 3、启动Netty服务器
        String host = "localhost";
        int port = 8080;
        HttpServer.create().host(host).port(port).handle(adapter).bindNow(); // 现在就绑定
        try {
            System.out.println("服务器启动成功，访问地址：http://" + host + ":" + port);
            System.in.read();
            System.out.println("服务器停止......");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
