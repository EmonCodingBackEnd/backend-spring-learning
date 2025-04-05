package com.coding.reactor.projectreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoAPITests {

    /**
     * 直接创建 Mono（如 Mono.just()）会在声明阶段立即捕获数据，而 Mono.defer 推迟到订阅阶段才执行。
     * 特点：
     * 1、延迟执行：只有在订阅（subscribe()）时才会触发 Mono 的创建逻辑。
     * 2、动态生成：每次订阅都会重新执行 Mono 的创建逻辑（适合需要动态数据的场景）。
     * 3、避免缓存：防止在声明阶段就固定数据，确保每次订阅获取最新结果。
     * 典型使用场景：
     * 1、动态数据源（如每次订阅需重新查询数据库）。
     * 2、延迟副作用操作（如订阅时才发送 HTTP 请求）。
     * 3、避免 Mono 创建时的立即执行（如 Mono.error 会直接抛出异常，用 defer 可延迟异常触发）。
     */
    @Test
    void defer() throws InterruptedException {
        // 直接创建（时间戳固定）
        Mono<String> monoJust = Mono.just("Time: " + System.currentTimeMillis());
        // 延迟创建（每次订阅重新获取时间戳）
        Mono<String> monoDefer = Mono.defer(() ->
                Mono.just("Deferred Time: " + System.currentTimeMillis())
        );

        // 第一次订阅
        monoJust.subscribe(System.out::println);  // 输出声明时的时间戳
        monoDefer.subscribe(System.out::println); // 输出订阅时的新时间戳

        // 第二次订阅（延迟创建的 Mono 会重新计算）
        Thread.sleep(1000);
        monoJust.subscribe(System.out::println);  // 时间戳不变（与第一次相同）
        monoDefer.subscribe(System.out::println); // 时间戳更新（1秒后）
    }

    @Test
    void empty() {
        // 流里面没有元素，只有完成信号/结束信号
        Mono.empty().defaultIfEmpty("default").log().subscribe(); // defaultIfEmpty 静态兜底方法
        Mono.empty().switchIfEmpty(Mono.just("哈哈")).log().subscribe(); // switchIfEmpty 动态兜底方法
    }

    @Test
    void flagMap() {
        Mono.just("zhang san").flatMap(name -> Mono.just(name.toUpperCase())).log().subscribe();
    }

    @Test
    void flagMapMany() {
        Mono.just("zhang san").flatMapMany(name -> Flux.fromArray(name.split(" "))).log().subscribe();
    }
}
