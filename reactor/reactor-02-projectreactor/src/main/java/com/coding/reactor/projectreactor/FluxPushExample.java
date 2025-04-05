package com.coding.reactor.projectreactor;

import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

public class FluxPushExample {
    public static void main(String[] args) {
        MyEventProcessor<String> myEventProcessor = new MyEventProcessor<>();
        Flux<String> bridge = Flux.push(sink -> {
            // 先注册事件，所有这些操作都是在 myEventProcessor 执行时异步完成的。
            myEventProcessor.register(
                    new SingleThreadEventListener<>() {
                        public void onDataChunk(List<String> chunk) {
                            for (String s : chunk) {
                                // 事件由单个监听线程使用 next 推送到接收器。
                                sink.next(s);
                            }
                        }

                        public void processComplete() {
                            // 由同一监听线程生成 complete 事件。
                            sink.complete();
                        }

                        public void processError(Throwable e) {
                            // 由同一监听线程生成的事件
                            sink.error(e);
                        }
                    });
        });
        bridge.log().subscribe();

        // 5秒之后触发
        try {
            Thread.sleep(5000);
            // 启动一个线程，模拟外部事件；若此处启动多个线程，也不会报错，但会数据丢失
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    interface SingleThreadEventListener<T> {
        void onDataChunk(List<T> chunk);

        void processComplete();

        void processError(Throwable e);
    }

    static class MyEventProcessor<T> {
        private SingleThreadEventListener<T> listener;

        public void register(SingleThreadEventListener<T> listener) {
            this.listener = listener;
        }
    }
}
