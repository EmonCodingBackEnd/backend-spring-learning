package com.coding.reactor.projectreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class SinksTests {
    @Test
    void sinks() {
        Sinks.many().unicast(); // 单播：这个管道只能绑定单个订阅者（消费者）
        Sinks.many().multicast(); // 多播：这个管道能绑定多个订阅者
        Sinks.many().replay(); // 重放：这个管道能重放元素。是否给后来的订阅者把之前的元素依然发给它；从头消费还是从订阅的那一刻消费

//        Sinks.Many<Object> objectMany = Sinks.many().unicast().onBackpressureBuffer(); // 背压队列
//        Sinks.Many<Object> objectMany = Sinks.many().multicast().onBackpressureBuffer(); //
        Sinks.Many<Object> objectMany = Sinks.many().replay().limit(3); // 重放3个元素
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                objectMany.tryEmitNext("i" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        objectMany.asFlux().subscribe(v -> System.out.println("v1 = " + v));
        // 5秒之后开始订阅，默认订阅者，从订阅的那一刻开始接收元素
        new Thread(() -> {
            {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                objectMany.asFlux().subscribe(v -> System.out.println("v2 = " + v));
            }
        }).start();

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void cache() {
        Flux<Integer> cache = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .cache(2) // 缓存元素，不设置 cache 时缓存所有，设置后仅缓存指定个数的元素
                ;
        cache.subscribe();

        new Thread(() -> {
            {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cache.subscribe(v -> System.out.println("v = " + v));
            }
        }).start();

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void block() {
        Mono<List<Integer>> listMono = Flux.just(1, 2, 3, 4).map(i -> i + 10).collectList();
        System.out.println(listMono.block());
    }

    @Test
    void parallelFlux() {
        Flux.range(1, 100)
                .buffer(10)
                .parallel(8) // 并行化，8个线程
                .runOn(Schedulers.newParallel("yy"))
                .log()
                .flatMap(Flux::fromIterable)
                .collectSortedList(Integer::compareTo)
                .subscribe(v -> System.out.println("v = " + v));
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
