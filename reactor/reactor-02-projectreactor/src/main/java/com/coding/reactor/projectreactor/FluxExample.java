package com.coding.reactor.projectreactor;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.SignalType;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class FluxExample {
    /**
     * 在 Project Reactor 的 Flux 中，doOnXXX 方法用于在流的生命周期中添加副作用操作（如日志、监控、调试），不影响数据流本身，仅观察事件。以下是常见方法汇总：
     * 核心方法
     * doOnSubscribe(Consumer<Subscription>)
     * 触发时机：订阅时（Subscription 创建后）。
     * 用途：记录订阅事件，初始化资源。
     * doOnNext(Consumer<T>)
     * 触发时机：每个元素发出前。
     * 用途：记录/处理元素（如打印日志）。
     * doOnError(Consumer<Throwable>)
     * 触发时机：流发生错误时。
     * 用途：错误日志、告警。
     * doOnComplete(Runnable)
     * 触发时机：流正常完成（onComplete 信号发出前）。
     * 用途：完成事件处理（如释放资源）。
     * doOnCancel(Runnable)
     * 触发时机：流被取消时。
     * 用途：取消事件处理（如清理状态）。
     * doOnRequest(LongConsumer)
     * 触发时机：下游请求数据时（背压机制）。
     * 用途：监控请求量（如跟踪背压请求次数）。
     * <p>
     * 高级方法
     * doOnTerminate(Runnable)
     * 触发时机：流终止前（包括 onComplete 或 onError）。
     * 注意：无论成功或失败均触发，但无法区分结果。
     * doAfterTerminate(Runnable)
     * 触发时机：流终止后（onComplete/onError 信号已发出）。
     * 用途：后置清理（如关闭连接）。
     * doOnEach(Consumer<Signal<T>>)
     * 触发时机：所有信号（onNext/onComplete/onError）发出时。
     * 用途：统一处理所有信号（可访问具体事件细节）。
     * doFinally(Consumer<SignalType>)
     * 触发时机：流结束（包括正常完成、错误、取消）。
     * 特点：通过 SignalType 区分终止原因（如 ON_COMPLETE、ON_ERROR）。
     * <p>
     * 特点总结
     * 副作用操作：不修改数据流，仅观察或执行额外逻辑。
     * 调试友好：常用于日志、性能监控、资源管理。
     * 执行顺序：按方法调用顺序触发（如 doOnSubscribe → doOnNext → doOnComplete）。
     * 线程安全：需确保副作用操作是线程安全的（尤其在并发场景）。
     *
     * @param args
     */
    public static void main(String[] args) {


        // ==================================================华丽的分割线==================================================

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    @AllArgsConstructor
    static class MyListener {
        FluxSink<Object> sink;

        /**
         * 用户登录触发online监听
         */
        public void online(String userName) {
            System.out.println("用户登录触发online监听：" + userName);
            sink.next(userName);
        }
    }

    public static void main1(String[] args) {
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);
        Flux<String> seq3 = Flux.from(seq1);
        // 注意，即使工厂方法没有值，它也仍然遵循泛型类型。
        Flux<Object> seq4 = Flux.empty();
        // 第一个参数是范围的开始，而第二个参数是要生成的项目数量。
        Flux<Integer> seq5 = Flux.range(5, 3);
        // ==================================================华丽的分割线==================================================

        Consumer<Object> consumer = v -> System.out.println("v = " + v);
        Consumer<? super Throwable> errorConsumer = throwable -> System.out.println("throwable = " + throwable);
        Runnable completeConsumer = () -> System.out.println("流结束了...");
        Consumer<? super Subscription> subscriptionConsumer = subscription -> System.out.println("subscription = " + subscription);

        // 订阅并触发序列。
        Flux.empty().subscribe();
        // 对每个产生的值进行处理。
        Flux.empty().subscribe(consumer);
        // 处理值，同时响应错误。
        Flux.empty().subscribe(consumer, errorConsumer);
        // 处理值和错误，并在序列成功完成时运行一些代码。
        Flux.empty().subscribe(consumer, errorConsumer, completeConsumer);
        // 处理值和错误以及成功完成，但也要对由此 subscribe 调用产生的 Subscription 进行一些操作。
        Flux.empty().subscribe(consumer, errorConsumer, completeConsumer, subscriptionConsumer);
        Flux.empty().subscribe(new BaseSubscriber<Object>() {
        });
    }

    public static void main2(String[] args) {
        Flux<Integer> ints = Flux.range(1, 3);
        // 以最简单的方式订阅。
        ints.subscribe();

        // 1.多个元素的流
        Flux<Integer> just = Flux.just(1, 2, 3, 4, 5);
        // 流不消费就没用；消费：订阅
        just.subscribe(System.out::println);
        // 一个数据流可以有多个消费者
        // 对于每个消费者来说流都是一样的；广播模式传递；
        just.subscribe(e -> System.out.println("subscribe2:" + e));

        try {
            Flux<Integer> ints2 = Flux.range(1, 4)
                    .map(i -> {
                        if (i <= 3) return i;
                        throw new RuntimeException("Got to 4");
                    });
            ints2.onErrorComplete() // 忽略异常，流错误的时候，把错误吃掉，转为正常信号
                    .subscribe(System.out::println,
                            error -> System.err.println("Error: " + error));
        } catch (Exception ignored) {
        }

        System.out.println("--------------------------------------------------");
        Flux<Integer> ints3 = Flux.range(1, 4);
        ints3.subscribe(System.out::println,
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"));
    }

    public static void main3(String[] args) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1)); // 每秒产生一个从0开始的递增数字
        interval.subscribe(System.out::println);

        // 空流，有一个信号：此时代表完成信号
        Flux<Object> empty = Flux.empty().doOnComplete(() -> System.out.println("empty流结束了......"));
        empty.subscribe(System.out::println);

        Flux.just(1, 2, 3, 4, 5).subscribe(
                v -> System.out.println("v = " + v),// 流元素消费
                throwable -> System.out.println("throwable = " + throwable), // 感知异常结束
                () -> System.out.println("流结束了...") // 感知正常结束
        );

        Flux.range(1, 10)
                .doOnRequest(r -> System.out.println("request of " + r))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    public void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }

                    @Override
                    public void hookOnNext(Integer integer) {
                        System.out.println("Cancelling after having received " + integer);
                        cancel();
                    }
                });
    }

    public static void main4(String[] args) {
        // 事件感知：当流发生什么事的时候，触发一个回调。
        Flux<Integer> integerFlux = Flux.range(1, 100).filter(e -> e <= 7)
                // .map(e -> e == 6 ? e / 0 : e) // 抛出异常，流结束，会触发 hookOnError；会触发 doOnError；不会触发 doOnCancel
                // 延迟每个元素的发射时间，使每个元素在指定的时间间隔后才被处理。参数为 Duration 类型，表示延迟的时间长度。
                .delayElements(Duration.ofSeconds(1))
                // 为Flux流添加一个订阅时的副作用操作
                .doOnSubscribe(subscription -> System.out.println("just流被订阅者订阅了......" + subscription))
                // 在每个元素被消费时执行指定的操作
                .doOnNext(i -> System.out.println("doOnNext......" + i))
                // 当Flux中的所有元素都被成功处理且没有发生异常时，触发doOnComplete回调。
                .doOnComplete(() -> System.out.println("just流正常结束了......"))
                // 当数据流中发生异常时，会触发
                .doOnError(e -> System.out.println("just流出错了......" + e))
                // 流被取消时执行指定的操作。当订阅者调用 cancel() 方法或流因其他原因被取消时，会触发此回调。
                .doOnCancel(() -> System.out.println("just流被取消了......"))
                // 为Flux流中的元素和每个信号（包括 onNext、onError 和 onComplete）[注意，没有onSubscribe、onCancel]添加一个回调操作
                // 信号有哪些？参考：reactor.core.publisher.SignalType
                .doOnEach(signal -> System.out.println("doOnEach......" + signal.getType()));
        integerFlux.subscribe(new BaseSubscriber<Integer>() {

            // 生命周期钩子1： 订阅关系绑定的时候触发
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("订阅者和发布者绑定......" + subscription);
                // 找发布者要数据
                request(1);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("元素到达......" + value);
                if (value < 5) {
                    if (value == 3) {
                        // 会触发 hookOnError；不会触发 doOnError，但会触发 doOnCancel
                        // throw new RuntimeException("数据流异常");
                    }
                } else if (value == 7) {
                    // cancel();
                }
                request(1);
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("数据流完成......");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println("数据流异常......" + throwable);
            }

            @Override
            protected void hookOnCancel() {
                System.out.println("数据流被取消......");
            }

            @Override
            protected void hookFinally(SignalType type) {
                System.out.println("数据流必做之事......" + type);
            }
        });
    }

    public static void main5(String[] args) {
        Flux.concat(Flux.just(1, 2, 3), Flux.just(7, 8, 9)).subscribe(System.out::println);

        Disposable subscribe = Flux.range(1, 7)
                .delayElements(Duration.ofSeconds(1))
                .log()
                .filter(i -> i > 2)
                .map(i -> "haha-" + i)
                .subscribe(System.out::println);
        System.out.println("subscribe.isDisposed() = " + subscribe.isDisposed());
        try {
            Thread.sleep(5000);
            subscribe.dispose();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("subscribe.isDisposed() = " + subscribe.isDisposed());

        // ==================================================华丽的分割线==================================================
        // 10元素，buffer(3)；消费者请求4次，数据消费完成
        Flux.range(1, 10)
                .buffer(3) // 缓冲区：缓冲3个元素: 消费一次最多可以拿到三个元素； 凑满数批量发给消费者
                .log()
                .subscribe(System.out::println);

        Flux.range(1, 1000)
                .log()
                //限流触发，看上游是怎么限流获取数据的
                .limitRate(100) // 一次预取100个元素； 第一次 request(100)，以后request(75)
                .subscribe();
    }

    public static void main6(String[] args) {
        Flux<Object> generate = Flux.generate(
                // 我们提供初始状态值 0。
                () -> 0,
                (state, sink) -> {
                    if (state == 7) {
                        sink.error(new RuntimeException("我不太喜欢7"));
                    }
                    if (state <= 10) {
                        sink.next("Generated: " + state); // 我们使用状态来选择要发射的内容。
                    } else {
                        sink.complete(); // 我们也用它来选择何时停止。
                    }
                    return state + 1; // 我们返回一个新的状态，我们将在下一次调用中使用它（除非序列在此终止）。
                }
        );
        generate.log().subscribe();

        // 可变状态变体
        Flux<String> flux = Flux.generate(
                AtomicLong::new, // 可变对象作为状态
                (state, sink) -> {
                    long i = state.getAndIncrement(); // 我们在这里修改状态。
                    sink.next("3 x " + i + " = " + 3 * i);
                    if (i == 10) sink.complete();
                    return state; // 返回相同的实例作为新状态。
                },
                (state) -> System.out.println("state: " + state) // 我们看到最后一个状态值（11）作为此 Consumer lambda 的输出。
        );
        flux.subscribe(System.out::println);
    }
}
