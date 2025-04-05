package com.coding.reactor.projectreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

public class ContextTests {

    /**
     * ThreadLocal在响应式编程中无法使用。
     * 响应式中，数据流期间共享数据，Context API: Context: 读写；ContextView：只读。
     * <p>
     * 响应式流的“组装时”与“订阅时”分离
     * Reactor 的操作符（如 map、filter、contextWrite）在组装阶段（定义流时）被声明，但实际执行是在订阅阶段（调用 subscribe() 时）。
     * <p>
     * 上下文的传递是基于订阅的，而订阅是从下游到上游（即从最后一个操作符到第一个操作符）触发的。因此，contextWrite 的生效顺序需要与订阅顺序一致。
     */
    @Test
    void threadlocal() {
        Flux.just(1, 2, 3)
                .transformDeferredContextual((flux, context) -> {
                    System.out.println("context = " + context);
                    return flux.map(i -> context.get("prefix").toString() + i);
                })
                // 上游能拿到下游的最近一次数据
                .contextWrite(Context.of("prefix", "哈哈")) // ThreadLocal共享了数据，上游的所有人能看到；Context由下游传播给上游
                .subscribe(v -> System.out.println("v = " + v));
    }

}
