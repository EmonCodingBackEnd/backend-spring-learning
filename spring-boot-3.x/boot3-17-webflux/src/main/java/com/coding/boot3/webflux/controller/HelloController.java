package com.coding.boot3.webflux.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class HelloController {

    // WebFlux:兼容SpringMVC的大多数注解
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "key", required = false, defaultValue = "haha") String key,
                        ServerWebExchange exchange,
                        HttpMethod method,
                        WebSession webSession,
                        HttpEntity<String> httpEntity,
                        @RequestBody String body,
                        MultipartFile file, @RequestParam("file") FilePart filePart) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String name = method.name();
        Object user = webSession.getAttribute("user");
        webSession.getAttributes().put("user", "wenqiu");
//        filePart.transferTo() // 零拷贝技术

        return "Hello World! key = " + key;
    }

    @GetMapping("/re")
    public ResponseEntity<String> re() {
//        ResponseEntity<String> re = ResponseEntity.ok()
//                .header("aaa", "bbb")
//                .contentType(MediaType.APPLICATION_CBOR)
//                .body("Hello World!");
        return ResponseEntity.ok("Hello World!");
    }

    /**
     * 现在推荐的方式
     * 1、返回单个数据Mono：Mono<T>
     * 2、返回多个数据Flux：Flux<T>
     * 3、配合Flux，完成SSE：Server-Sent Events；服务端事件推送
     */
    @GetMapping("/haha")
    public Mono<String> haha(@RequestParam(value = "haha", required = false, defaultValue = "1") Integer haha) {
        return Mono.just("haha");
    }

    @GetMapping("/hehe")
    public Flux<String> hehe() {
        return Flux.just("hehe1", "hehe2", "hehe3");
    }

    // SSE测试；chatgpt都在用；服务端推送
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sse() {
        return Flux.range(1, 10)
                .map(i -> "sse-" + i)
                .delayElements(Duration.ofMillis(500));
    }

    @GetMapping(value = "/sse2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> sse2() {
        return Flux.range(1, 10)
                .map(i -> {
                    return ServerSentEvent.builder(i + "-" + System.currentTimeMillis())
                            .id(i + "")
                            .event("haha-" + i)
                            .comment("哈哈-" + i)
                            .build();
                })
                .delayElements(Duration.ofMillis(500));
    }

    @GetMapping("/error")
    public Mono<String> error() {
        return Mono.just(0).map(i -> 10 / i + "");
    }

    // SpringMVC 以前怎么用，基本可以无缝切换。
    // 底层：需要自己开始编写响应式代码
}
