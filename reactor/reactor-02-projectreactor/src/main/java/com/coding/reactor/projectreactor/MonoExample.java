package com.coding.reactor.projectreactor;

import reactor.core.publisher.Mono;

public class MonoExample {
    public static void main(String[] args) {
        // 0或1个元素的流
        Mono<Integer> just = Mono.just(1);
        Mono<String> noData = Mono.empty();
        Mono<String> data = Mono.just("foo");
    }
}
