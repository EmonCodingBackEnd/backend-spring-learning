package com.coding.reactor.stream.function;

import java.util.UUID;
import java.util.function.*;

public class FunctionExample {

    public static void main(String[] args) {
        // 有入参，无出参【**消费者**】：function.accept
        BiConsumer<String, String> consumer = (a, b) -> {
            System.out.println("哈哈：" + a + "；呵呵：" + b);
        };
        consumer.accept("1", "2");

        // 有入参，有出参【**多功能函数**】
        Function<String, Integer> function = Integer::parseInt;
        System.out.println(function.apply("123"));

        // 无入参，无出参【**普通函数**】
        Runnable runnable = () -> System.out.println("runnable");
        new Thread(runnable).start();

        // 无入参，有出参【**生产者**】：function.get
        Supplier<String> supplier = () -> UUID.randomUUID().toString();
        System.out.println(supplier.get());

        BiFunction<String, Integer, Long> biFunction = (a, b) -> 8L;

        Predicate<Integer> even = t -> t % 2 == 0;
        System.out.println(even.test(2));
        System.out.println(even.negate().test(2));

        composeTest();
    }

    public static void composeTest() {
        // 1、定义数据提供者函数
        Supplier<String> supplier = () -> String.valueOf(42);
        // 2、断言：验证是否一个数字
        Predicate<String> isNumber = str -> str.matches("-?\\d+(\\.\\d+)?");
        // 3、转换器：把字符串变成数字
        Function<String, Integer> toNumber = Integer::parseInt;
        // 4、消费者：打印数字
        Consumer<Integer> consumer = integer -> {
            if (integer % 2 == 0) {
                System.out.println("偶数：" + integer);
            } else {
                System.out.println("级数：" + integer * 2);
            }
        };

        // 串在一起，实现判断42这个字符串时偶数还是奇数
        composeFunc(supplier, isNumber, toNumber, consumer);
        composeFunc(() -> String.valueOf(777), str -> str.matches("-?\\d+(\\.\\d+)?"), Integer::parseInt, System.out::println);
    }

    private static void composeFunc(Supplier<String> supplier, Predicate<String> isNumber, Function<String, Integer> toNumber, Consumer<Integer> consumer) {
        if (isNumber.test(supplier.get())) {
            // 说明是一个数字
            consumer.accept(toNumber.apply(supplier.get()));
        } else {
            System.out.println("不是数字");
        }
    }

}
