package com.coding.reactor.projectreactor;

import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

public class FluxCreateExample {
    public static void main(String[] args) {
        MyEventProcessor<String> myEventProcessor = new MyEventProcessor<>();
        Flux<String> bridge = Flux.create(sink -> {
            // 先注册事件，所有这些操作都是在 myEventProcessor 执行时异步完成的。
            myEventProcessor.register(
                    new MyEventListener<>() {
                        public void onDataChunk(List<String> chunk) {
                            for (String s : chunk) {
                                sink.next(s);
                            }
                        }

                        public void processComplete() {
                            sink.complete();
                        }
                    });
        });
        bridge.log().subscribe();

        // 5秒之后触发
        try {
            Thread.sleep(5000);
            // 启动一个线程，模拟外部事件
            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(100);
                        myEventProcessor.listener.onDataChunk(List.of("a" + i, "b" + i, "c" + i));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                myEventProcessor.listener.processComplete();
            }).start();
            // 启动一个线程，模拟外部事件
            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(100);
                        myEventProcessor.listener.onDataChunk(List.of("x" + i, "y" + i, "z" + i));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                myEventProcessor.listener.processComplete();
            }).start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    interface MyEventListener<T> {
        void onDataChunk(List<T> chunk);

        void processComplete();
    }

    static class MyEventProcessor<T> {
        private MyEventListener<T> listener;

        public void register(MyEventListener<T> listener) {
            this.listener = listener;
        }
    }
}
