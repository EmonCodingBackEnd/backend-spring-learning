package com.coding.reactor.projectreactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class ThreadAndSchedule {

    public static void main1(String[] args) throws InterruptedException {
        final Mono<String> mono = Mono.just("hello "); // Mono<String> 在线程 main 中被组装。

        Thread t = new Thread(() -> mono
                .map(msg -> msg + "thread ")
                .subscribe(v -> // 然而，它在线程 Thread-0 中进行了订阅。
                        System.out.println(v + Thread.currentThread().getName()) // 因此， map 和 onNext 回调实际上都在 Thread-0 中运行。

                )
        );
        t.start();
        t.join();

    }

    public static void main2(String[] args) {
        Flux.interval(Duration.ofMillis(300), Schedulers.newSingle("test")).log().subscribe();
    }

    public static void main3(String[] args) throws InterruptedException {
        // 创建一个由四个 Thread 实例支持的新的 Scheduler
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i)
                .publishOn(s)
                .map(i -> "value " + i + "-" + Thread.currentThread().getName()); // 这里的打印，使用的是线程调度器 s 中的线程

        //只要不指定线程池，默认发布者用的线程就是订阅者的线程；
        Thread t = new Thread(() -> flux.subscribe(System.out::println));
        t.start();
        t.join();
    }

    public static void main4(String[] args) throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .log()
                .map(i -> 10 + i) // 这里使用的是线程调度器 s 中的线程
                .subscribeOn(s)
                .map(i -> "value " + i + "-" + Thread.currentThread().getName()); // 这里的打印，使用的是线程调度器 s 中的线程

        Thread t = new Thread(() -> flux.subscribe(System.out::println));
        t.start();
        t.join();
    }

    public static void main(String[] args) throws InterruptedException {
        Scheduler p = Schedulers.newParallel("parallel1-scheduler", 4);
        Scheduler s = Schedulers.newParallel("parallel2-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .log()
                .publishOn(p)
                .map(i -> {
                    System.out.println(Thread.currentThread().getName()); // 这里使用的是线程调度器 p 中的线程
                    return 10 + i;
                })
                .subscribeOn(s)
                .map(i -> "value " + i + "-" + Thread.currentThread().getName()); // 这里的打印，使用的是线程调度器 p 中的线程

        Thread t = new Thread(() -> flux.subscribe(System.out::println));
        t.start();
        t.join();
    }
}
