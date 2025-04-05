package com.coding.boot3.aot.normal;

/**
 * 打包成本地镜像
 * 1、打成jar包：
 *  若碰到执行（java -jar aot-normal.jar ）失败：aot-normal.jar中没有主清单属性
 *  可执行：java -cp aot-normal.jar com.coding.boot3.aot.normal.MainApplication
 *
 *  也可以：注意修改 jar 包内的 MANIFEST.MF 文件，添加 Main-Class 属性，值为 com.coding.boot3.aot.normal.MainApplication
 *  Main-Class: com.coding.boot3.aot.normal.MainApplication
 *  然后可以 java -jar aot-normal.jar 运行
 *
 * 2、打成本地镜像（可执行文件）：
 *  native-image -cp aot-normal.jar com.coding.boot3.aot.normal.MainApplication -o 输出的文件名
 *  并不是所有的Java代码都能支持本地打包；SpringBoot保证Spring应用的所有程序都能在AOT的时候提前告知 graalvm 怎么处理。
 *  比如：
 *  - 动态能力：反射的代码：（动态获取构造器、反射创建对象、反射调用一些方法）
 *      解决方案：额外处理（SpringBoot提供了一些注解）：提前告知 graalvm 反射会用到哪些方法、构造器
 *  - 配置文件损失：
 *      解决方案：额外处理（配置中心）：提前告知 graalvm 配置文件怎么处理
 *  一个标准：二进制里面不鞥包含的、不能动态的都得提前处理。
 */
public class MainApplication {

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
