package com.coding.reactor.projectreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class FluxAPITests {

    @Test
    void filter() {
        Flux.just(1, 2, 3, 4).filter(s -> s % 2 == 0).log().subscribe();
    }

    /**
     * map：同步转换，简单高效，适合无阻塞操作。
     * 简单同步 → map
     * <p>
     * 为什么 concatMap 会比 map 慢？
     * concatMap 会等待前一个异步操作完成后才处理下一个元素，而 map 是同步立即执行。
     * <p>
     * 什么情况下该用 map 而不是 concatMap？
     * 如果转换逻辑是纯同步的（如字符串拼接、数学运算），优先用 map，性能更高。
     * 如果需要调用异步服务（如数据库、HTTP），则必须用 concatMap 或 flatMap。
     */
    @Test
    void map() {
        Flux.just("zhang san", "li si").map(String::toUpperCase).log().subscribe();
    }

    /**
     * flatMap: 无序并行处理
     * 特点：
     * 异步并行：所有元素的转换操作并行执行（不保证顺序）。
     * 输出顺序不固定：先完成的操作先输出。
     * 适合场景：对顺序无严格要求，需要最大化吞吐量的异步任务（如并发 HTTP 请求）。
     * flatMap：并行 + 无序 → 最大化吞吐，适合顺序无关任务。
     */
    @Test
    void flatMap() {
        Flux.just(1, 2, 3)
                .flatMap(id -> Mono.just("Processed-" + id).delayElement(Duration.ofMillis(100L * (4 - id)))) // 模拟延迟（3最快，1最慢）
                .subscribe(System.out::println);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * flatMapSequential: 有序并行处理
     * 特点：
     * 异步并行：所有元素的转换操作并行执行。
     * 输出顺序固定：按原始顺序输出（内部并行，但结果重新排序）。
     * 适合场景：需要并行提升性能，但最终结果必须保持顺序（如数据库批量查询）。
     * flatMapSequential：并行 + 有序 → 平衡性能与顺序，适合需顺序的场景。
     */
    @Test
    void flatMapSequential() {
        Flux.just(1, 2, 3)
                .flatMapSequential(id -> Mono.just("Processed-" + id).delayElement(Duration.ofMillis(100L * (4 - id)))) // 模拟延迟（3最快，1最慢）
                .subscribe(System.out::println);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void concat() {
        // 多个流连接起来，每个流之间类型可以不一致
        Flux.concat(Flux.just(1, 2, 3), Flux.just('x', 'y', 'z')).log().subscribe();
    }

    /**
     * concatMap：异步顺序转换，适合需要严格顺序的异步任务。
     * 一个元素可以变很多元素，对于元素类型无限制
     * concatMap：串行 + 有序 → 严格顺序，但性能最低（参考之前对比）。
     */
    @Test
    void concatMap() {
        Flux.just("zhang san", "li si").concatMap(s -> Flux.just(s + 1, s + 2, s + 3).delayElements(Duration.ofMillis(100 * new Random().nextInt(10)))).log().subscribe();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void concatWith() {
        // 连接的流和老流中的元素类型必须一致
        Flux.just("zhang san", "li si").concatWith(Flux.just("wang wu")).log().subscribe();
    }

    @Test
    void transform() {
        AtomicInteger count = new AtomicInteger();
        Flux<String> flux = Flux.just("a", "b", "c").transform(s -> {
            // 如果是第一次调用，老流中的所有元素转成大写
            if (count.incrementAndGet() == 1) {
                return s.map(String::toUpperCase);
            } else {
                return s;
            }
        }).log();

        // transform无defer时，不会共享外部变量的值
        flux.subscribe(v -> System.out.println("订阅者1：v=" + v));
        flux.subscribe(v -> System.out.println("订阅者2：v=" + v));
    }

    @Test
    void transformDeferred() {
        AtomicInteger count = new AtomicInteger();
        Flux<String> flux = Flux.just("a", "b", "c").transformDeferred(s -> {
            // 如果是第一次调用，老流中的所有元素转成大写
            if (count.incrementAndGet() == 1) {
                return s.map(String::toUpperCase);
            } else {
                return s;
            }
        }).log();

        // transformDeferred，会共享外部变量的值。有状态转换
        flux.subscribe(v -> System.out.println("订阅者1：v=" + v));
        flux.subscribe(v -> System.out.println("订阅者2：v=" + v));
    }

    @Test
    void empty() {
        // 流里面没有元素，只有完成信号/结束信号
        Flux.empty().defaultIfEmpty("default").log().subscribe(); // defaultIfEmpty 静态兜底方法
        Flux.empty().switchIfEmpty(Flux.just("哈哈")).log().subscribe(); // switchIfEmpty 动态兜底方法
    }

    /**
     * concat：连接；A流所有元素和B流所有元素拼接
     * merge：合并；A流所有元素和B流所有元素按照时间顺序合并
     */
    @Test
    void merge() {
        Flux.merge(
                Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1)),
                Flux.just("x", "y", "z").delayElements(Duration.ofMillis(1500)),
                Flux.just("张三", "李四", "王五", "赵六", "田七").delayElements(Duration.ofMillis(800))
        ).log().subscribe();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void mergeWith() {
        // 连接的流和老流中的元素类型必须一致
        Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1))
                .mergeWith(Flux.just(4, 5, 6).delayElements(Duration.ofMillis(600)))
                .log().subscribe();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 操作符	            订阅时机
     * Flux.concat	        严格顺序订阅：前一个 Publisher 完全结束（onComplete） 后，才订阅下一个。
     * Flux.mergeSequential	立即顺序订阅：按顺序逐个订阅 Publisher，但不等待前一个完成，只是缓冲数据以保证输出顺序。
     */
    @Test
    void mergeSequential() {
        // 按照哪一个流先发送元素排队
        // 定义 3 个异步数据流（模拟网络请求或耗时操作）
        Flux<String> flux1 = Flux.just("A1", "A2").delayElements(java.time.Duration.ofMillis(200));
        Flux<String> flux2 = Flux.just("B1", "B2").delayElements(java.time.Duration.ofMillis(100));
        Mono<String> mono3 = Mono.just("C1").delayElement(java.time.Duration.ofMillis(50));

        // 使用 mergeSequential 合并
        Flux<String> mergedFlux = Flux.mergeSequential(flux1, flux2, mono3);

        // 订阅并打印结果
        mergedFlux.log().subscribe();

        // 防止主线程退出（测试用）
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void zip() {
        /*
        [张三,1,x]
        [李四,2,y]
        [王五,3,z]
         */
        Flux.zip(Flux.just("张三", "李四", "王五", "赵六", "田七"),
                Flux.just(1, 2, 3),
                Flux.just("x", "y", "z")
        ).log().subscribe();
    }

    @Test
    void zipWith() {
        /*
        [[张三,1],x]
        [[李四,2],y]
        [[王五,3],z]
         */
        Flux.just("张三", "李四", "王五", "赵六", "田七").zipWith(Flux.just(1, 2, 3)).zipWith(Flux.just("x", "y", "z"))
                .log().subscribe();
    }

    /**
     * limitRequest vs take
     * 特性	            limitRequest(n)	            take(n)
     * 作用	            限制请求数量（背压控制）	    限制实际消费数量（数据截断）
     * 是否会 cancel()	❌ 不会主动取消	            ✅ 达到 n 后取消订阅
     * 适用场景	        精确控制请求量（如防止 OOM）	只取前 n 个数据
     * 是否影响后续操作符	✅ 影响后续所有请求	        ❌ 只影响自身
     */
    @Test
    void take() {
        // 取前几个元素
        Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")
                .take(3)
                .log().subscribe();
        // 在指定时间内取元素
        Flux<Long> infiniteStream = Flux.interval(Duration.ofMillis(200)); // 0, 1, 2, ...
        infiniteStream
                .take(Duration.ofSeconds(1)) // 只取 1 秒内发出的数据
                .subscribe(System.out::println);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void takeUntil() {
        // 直到条件满足时停止
        Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")
                .takeUntil(s -> s.equals("f"))
                .log().subscribe();
    }

    @Test
    void takeWhile() {
        // 只要条件满足就继续取
        Flux<Integer> numbers = Flux.range(1, 10);
        numbers.takeWhile(num -> num < 5)
                .subscribe(System.out::println);
    }

    @Test
    void takeLast() {
        // 取最后几个元素
        Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")
                .takeLast(3)
                .log().subscribe();
    }

    @Test
    void next() {
        Mono<Integer> next = Flux.just(1, 2, 3, 4, 5)
                .next(); // 取值第一个元素
        Integer block = next.block();
        System.out.println("block = " + block);
    }

    /**
     * 用于测量从订阅开始到每个元素发出所经过的时间（毫秒）。它返回一个 Tuple2<Long, T>
     */
    @Test
    void elapsed() {
        Flux.just("A", "B", "C")
                .elapsed() // 转换为 Tuple2<耗时, 数据>
                .subscribe(tuple ->
                        System.out.printf("元素: %s | 耗时: %dms\n", tuple.getT2(), tuple.getT1())
                );
    }

    @Test
    void elapsed2() {
        Flux.just("Task1", "Task2", "Task3")
                .flatMap(task ->
                        Mono.just(task)
                                .delayElement(Duration.ofMillis(ThreadLocalRandom.current().nextInt(50, 200)))
                                .elapsed() // 对每个异步任务单独计时
                                .map(tuple -> String.format("%s 完成 | 耗时: %dms", tuple.getT2(), tuple.getT1()))
                )
                .subscribe(System.out::println);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
