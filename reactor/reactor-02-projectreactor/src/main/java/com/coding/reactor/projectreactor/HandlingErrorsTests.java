package com.coding.reactor.projectreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;

public class HandlingErrorsTests {

    /**
     * 1、吃掉异常，消费者无异常感知
     * 2、返回一个默认值
     * 3、流直接中断，并以正常方式结束
     */
    @Test
    void onErrorReturn() {
        Flux.just(1, 2, 0, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorReturn("Divided by zero :(")
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    @Test
    void onErrorReturn2() {
        Flux.just(1, 2, 0, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorReturn(NullPointerException.class, "Divided by zero :(")
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    /**
     * 1、吃掉异常，消费者无异常感知
     * 2、调用一个兜底方法
     * 3、流直接中断，并以正常方式结束
     */
    @Test
    void onErrorResume() {
        Flux.just(1, 2, 0, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorResume(err -> Mono.just("Divided by zero :("))
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    @Test
    void onErrorResume2() {
        Flux.just(1, 2, 0, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorResume(err -> {
                            if (err instanceof NullPointerException) {
                                return Mono.just("Not Allow Divided by zero :(");
                            } else if (err instanceof ArithmeticException) {
                                return Mono.just("Divided by zero :(");
                            }
                            return Mono.error(err);
                        }
                )
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    /**
     * 1、吃掉异常
     * 2、抛出新异常，消费者有感知
     * 3、流程异常完成
     * 推荐使用onErrorMap
     */
    @Test
    void onErrorResume3() {
        Flux.just(1, 2, 0, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorResume(err ->
                        Flux.error(
                                new RuntimeException("oops, SLA exceeded", err))
                )
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    @Test
    void onErrorMap() {
        Flux.just(1, 2, 0, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorMap(original -> new RuntimeException("oops, SLA exceeded", original))
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    /**
     * 异常被捕获，做自己的事情
     * 不影响异常继续顺着流水线传播
     */
    @Test
    void doOnError() {
        Flux.just(1, 2, 0, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .doOnError(original -> {
                    System.out.println("original已被记录 = " + original);
                    throw new RuntimeException("oops, SLA exceeded", original);
                })
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    @Test
    void doFinally() {
        Flux.just(1, 2, 0, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .doOnError(original -> {
                    System.out.println("original已被记录 = " + original);
                })
                .doFinally(signalType -> {
                    System.out.println("流信号：" + signalType);
                })
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    /**
     * 吃掉错误，让流继续运行下去，不至于中断，最终流可以正常结束
     */
    @Test
    void onErrorContinue() {
        Flux.just(1, 2, 0, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .doOnError(original -> {
                    System.out.println("original已被记录 = " + original);
                })
                .onErrorContinue((throwable, o) -> {
                    System.out.println("err = " + throwable);
                    System.out.println("val = " + o);
                    System.out.println("发现" + o + "有问题了，继续执行");
                })
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    /**
     * 1、吃掉异常，消费者无异常感知
     * 2、流直接中断，并以正常方式结束
     */
    @Test
    void onErrorComplete() {
        Flux.just(1, 2, 0, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .doOnError(original -> {
                    System.out.println("original已被记录 = " + original);
                })
                .onErrorComplete()
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    /**
     * 操作符	        行为
     * onErrorResume	终止原始流，替换为新的 Publisher（如 Mono.empty()）。
     * onErrorContinue	吞掉错误，继续处理原始流的后续元素（需 Reactor 3.3.0+ 支持）。
     * onErrorStop	    强制错误传播到下游，阻止恢复操作符生效。
     */
    @Test
    void onErrorStop() {
        Flux.just(1, 2, 0, 5, 10, 20, 25, 50)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorStop() // 强制终止，阻止下游恢复；注意：不能放到onErrorContinue后面，必须在之前，才可以在错误时终止流
                .onErrorContinue((err, o) -> {
                    System.out.println("发现" + o + "有问题了，跳过该元素继续执行");
                })
                .subscribe(
                        v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束")
                );
    }

    @Test
    void retryAndTimeout() {
        Flux.interval(Duration.ofMillis(250))
                .map(input -> {
                    if (input < 3) return "tick " + input;
                    throw new RuntimeException("boom");
                })
                .retry(1) // 把流重头开始再执行一次
                .elapsed() // elapsed 将每个值与其上一个值发出的持续时间相关联。
                .subscribe(System.out::println, System.err::println); // 我们也想看到当出现 onError 时的情况。

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void retryWhenAndTimeout() {
        Mono.fromSupplier(() -> {
                    double random = Math.random();
                    System.out.println("random = " + random);
                    if (random > 0.5) {
                        throw new RuntimeException("服务调用失败");
                    }
                    return "服务调用成功";
                }).retryWhen(Retry
                        .backoff(3, Duration.ofSeconds(1)) // 最大3次重试，初始间隔1秒
                        .maxBackoff(Duration.ofSeconds(10)) // 最大间隔10秒
                        .filter(throwable -> throwable instanceof RuntimeException) // 仅重试IOException
                        .onRetryExhaustedThrow((spec, signal) ->
                                new RuntimeException("重试耗尽，最后错误: " + signal.failure()))
                )
                .subscribe(
                        System.out::println,
                        error -> System.err.println("最终错误: " + error)
                );

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
