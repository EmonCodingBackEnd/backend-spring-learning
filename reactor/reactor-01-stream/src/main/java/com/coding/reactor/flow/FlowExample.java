package com.coding.reactor.flow;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class FlowExample {

    // 定义流中间操作处理器；只用写订阅者的接口
    static class MyProcessor extends SubmissionPublisher<String> implements Flow.Processor<String, String> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            System.out.println("processor订阅绑定完成");
            this.subscription = subscription;
            this.subscription.request(1); // 找上游要一个数据
        }

        // 数据到达，出发这个回调
        @Override
        public void onNext(String item) {
            item = "haha-" + item;
            this.submit(item); // 把加工后的数据发出去
            this.subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println("processor接收到错误信号：" + throwable);
        }

        @Override
        public void onComplete() {
            System.out.println();
        }
    }

    /**
     * 核心概念：
     * 1、Publisher：发布者
     * 2、Subscriber：订阅者
     * 3、Subscription：订阅关系
     * 4、Processor：处理器；
     * ==================================================
     * 为什么是 Publisher.subscribe(Subscriber)？
     * 1. 责任分离：发布者主导订阅流程
     * 发布者（Publisher）：作为数据源，负责管理订阅者的注册、数据推送和背压协商。
     * 订阅者（Subscriber）：作为数据消费者，仅关注数据接收和请求管理（通过 Subscription）。
     * 设计逻辑：
     * 发布者需要掌握订阅的主动权，以便统一协调多个订阅者的请求和资源分配。
     * 2. 生命周期控制
     * 通过 Publisher.subscribe(Subscriber)，发布者可以：
     * 验证订阅者合法性（如拒绝重复订阅）。
     * 创建并绑定 Subscription（核心背压控制对象）。
     * 触发 onSubscribe 回调，初始化订阅关系。
     *
     * @param args
     */
    public static void main(String[] args) {
        // 1. 定义一个发布者：发布数据；
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // 4. 定义一个中间操作：给每个元素加个 哈哈 前缀
        Flow.Processor<String, String> processor = new MyProcessor();
        Flow.Processor<String, String> processor2 = new MyProcessor();

        // 2. 定义一个订阅者：订阅数据；
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {

            private Flow.Subscription subscription;

            // 在订阅时，会调用onSubscribe方法
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println(Thread.currentThread() + "订阅者，订阅开始了：" + subscription);
                this.subscription = subscription;

                // 从上游请求1个数据
                this.subscription.request(1);
            }

            // 在下一个元素到达时，执行这个回调；
            @Override
            public void onNext(String item) {
                System.out.println(Thread.currentThread() + "订阅者，接受到数据：" + item);
                if ("hello7".equals(item)) {
                    System.out.println(Thread.currentThread() + "订阅者，取消订阅");
                    this.subscription.cancel();
                } else {
                    this.subscription.request(1);
                }
            }

            // 在异常时
            @Override
            public void onError(Throwable throwable) {
                System.out.println(Thread.currentThread() + "订阅者，接收到错误信号：" + throwable);
            }

            // 在完成时
            @Override
            public void onComplete() {
                System.out.println(Thread.currentThread() + "订阅者，接收到完成信号！");
            }
        };

        // 3.绑定发布者和订阅者：绑定操作，就是发布者记住了订阅者都有谁，一旦有数据后，就给所有订阅者推送过去。链表关系绑定出责任链
        publisher.subscribe(processor); // 此时processor处理器相当于订阅者
        processor.subscribe(processor2); // 此时processor处理器相当于发布者，processor2处理器相当于订阅者
        processor2.subscribe(subscriber);  // 此时processor2处理器相当于发布者

        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread() + "发布者，发布数据：" + i);
            // 发布10条数据，publisher发布的所有数据在它的buffer区
            if (i == 9) {
                publisher.submit("hello" + i);
//                publisher.closeExceptionally(new RuntimeException("数据异常"));
//                publisher.submit("hello" + i / 0);
            } else {
                // 阻止发布过快，导致订阅者刚打算消费，就感知到发布者已关闭的情况，导致订阅者消费不到数据
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                publisher.submit("hello" + i);
            }
        }
        // 发布者通道关闭
        publisher.close();

        // 发布者有数据，订阅者就会拿到数据
        // JVM底层对于整个发布订阅关系做好了 异步+缓冲区处理 = 响应式系统

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
