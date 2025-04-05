package com.coding.reactor.stream.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// 函数式接口：只要是函数式接口就可以用Lambda表达式简化
// 函数式接口，接口中有且只有一个未实现的方法，这个接口就叫函数式接口
@FunctionalInterface
interface MyInterface {
    int sum(int i, int j);
}

@FunctionalInterface // 检查注解，帮我们快速检查我们写的接口是否是函数式接口
interface MyHaha {
    default void haha() {
    }

    void haha2();
}

// 1、自己写实现类
class MyInterfaceImpl implements MyInterface {
    @Override
    public int sum(int i, int j) {
        return i + j;
    }
}

public class Lambda {

    /**
     * Lambda简化函数式接口
     *
     * @param args
     */
    public static void simplifiedFunctionalInterface(String[] args) {
        // 1.自己创建实现类对象
        MyInterface myInterface = new MyInterfaceImpl();
        System.out.println(myInterface.sum(1, 2));

        // 2.创建匿名实现类
        MyInterface myInterface2 = new MyInterface() {
            @Override
            public int sum(int i, int j) {
                return i * i + j * j;
            }
        };
        System.out.println(myInterface2.sum(1, 2));

        // 3.使用lambda表达式
        MyInterface myInterface3 = (i, j) -> i * i + j * j;
        System.out.println(myInterface3.sum(1, 2));
    }

    public static void main(String[] args) {
        var names = new ArrayList<String>();
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        names.add("David");
        names.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        }); // 正序
        // 直接写函数式接口
        names.sort((o1, o2) -> o2.compareTo(o1)); // 倒序
        // 调用工具方法逆向排序
        names.sort(Comparator.naturalOrder()); // 正序
        names.sort(Comparator.reverseOrder()); // 正序
        // 类::方法；引用类中的实例方法
        names.sort(String::compareTo); // 正序

        System.out.println(names);

        // ==================================================华丽的分割线==================================================

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello，问秋！");
            }
        }).start();
        new Thread(() -> System.out.println("Hello，问秋！")).start();
    }
}
