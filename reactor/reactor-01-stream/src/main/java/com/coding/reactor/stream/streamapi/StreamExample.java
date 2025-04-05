package com.coding.reactor.stream.streamapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {
    public static void main(String[] args) {
        // 挑出最大偶数
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 1、for 循环，挨个遍历找到偶数，temp=i；下次找到的偶数和临时遍历比较
        int max = 0;
        for (Integer i : list) {
            if (i % 2 == 0 && i > max) {
                max = i;
            }
        }
        System.out.println("最大偶数：" + max);

        // 2、StreamAPI
        max = list.stream().filter(i -> i % 2 == 0).max(Integer::compareTo).orElse(0);
        System.out.println("最大偶数：" + max);

        // ==================================================华丽的分割线==================================================
        // 创建流
        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        Stream<Integer> concatedStream = Stream.concat(Stream.of(4, 5, 6), integerStream);
        concatedStream.forEach(System.out::println);
        Stream<Object> buildStream = Stream.builder().add("11").add("22").build();
        buildStream.forEach(System.out::println);
        Stream<Integer> iterateStream = Stream.iterate(0, i -> i + 2).limit(10);
        iterateStream.forEach(System.out::println);
        Stream<Integer> generateStream = Stream.generate(() -> 1).limit(10);
        generateStream.forEach(System.out::println);
        Stream<Integer> emptyStream = Stream.empty();
        Stream<String> ofStream = Stream.ofNullable("给定一个元素，该元素可为null");
        ofStream.forEach(System.out::println);


        // ==================================================华丽的分割线==================================================
        // 从集合容器中获取这个流
        List<Integer> integerList = List.of(1, 2);
        Stream<Integer> streamList = integerList.stream();

        Set<Integer> integersSet = Set.of(1, 2);
        Stream<Integer> streamSet = integersSet.stream();

        Map<Object, Object> objMap = Map.of("key1", "value1", 1, 2);
        Stream<Object> keyStream = objMap.keySet().stream();
        Stream<Object> valueStream = objMap.values().stream();
        Stream<Map.Entry<Object, Object>> entryStream = objMap.entrySet().stream();

        // ==================================================华丽的分割线==================================================

        // 流是并发还是不并发？和for有啥区别？流也是用 for 循环挨个处理：默认不并发，也可以并发；
        // 有状态数据并发后要自行解决多线程安全问题；
        // 流从所有操作都是无状态的操作，数据状态仅在此函数内有效，不溢出函数外。
        // 声明式：基于事件机制的回调
        long count = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).parallel().filter(i -> {
            System.out.println("使用filter线程：" + Thread.currentThread() + " 正在filter：" + i);
            return i % 2 == 0;
        }).count();
        System.out.println("count：" + count);


        // ==================================================华丽的分割线==================================================

        List<Person> personList = List.of(new Person("问秋", 18, "男"),
                new Person("张三", 20, "女"),
                new Person("李四", 16, "男"),
                new Person("王五", 28, "男"),
                new Person("赵六", 25, "男"),
                new Person("田七", 19, "女")
        );

        // 迭代器模式
        for (Person person : personList) {
            // 1、迭代速度取决于数据量
            // 2、数据还得有容器缓存
        }

        // 背压：数据的消费者根据自己的能力逐个处理数据；消费者通过反向压力（背压）主动调节生产者的数据推送速率，避免自身过载。
        // 正压：正向压力：数据的生产者给消费者压力
        personList.stream().filter(i -> i.age > 1);

        personList.stream() // 拿到集合的深拷贝，然后进行流操作
                .filter(person -> person.age > 18)
                .peek(person -> System.out.println("使用peek线程：" + Thread.currentThread() + " 正在peek：" + person))
                .map(Person::getName)
                .flatMap(e -> Arrays.stream(e.split("")))
                .distinct()
                .sorted(String::compareTo)
                .limit(3)
                .forEach(System.out::println);

        personList.stream().filter(person -> person.age > 15).collect(Collectors.groupingBy(t -> t.gender)).forEach((k, v) -> {
            System.out.println("key：" + k + " value：" + v);
        });

        // ==================================================华丽的分割线==================================================

        Stream.of(1, 2, 3, 4, 5, 6)
                .filter(i -> i > 2) // 无条件遍历流中的每个元素
                .toList()
                .forEach(System.out::println);

        System.out.println("Stream.of(1, 2, 3, 4, 5, 6)\n                .takeWhile(i -> i > 2) // 当满足条件，拿到这个元素，不满足直接结束流操作\n                .toList().size() = " + Stream.of(1, 2, 3, 4, 5, 6)
                .takeWhile(i -> i > 2) // 当满足条件，拿到这个元素，不满足直接结束流操作
                .toList().size());

        System.out.println("Stream.of(1, 2, 3, 4, 5, 6)\n                .takeWhile(i -> i < 2) // 当满足条件，拿到这个元素，不满足直接结束流操作\n                .toList().size() = " + Stream.of(1, 2, 3, 4, 5, 6)
                .takeWhile(i -> i < 2) // 当满足条件，拿到这个元素，不满足直接结束流操作
                .toList().size());

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Person {
        private String name;
        private Integer age;
        private String gender;
    }
}
