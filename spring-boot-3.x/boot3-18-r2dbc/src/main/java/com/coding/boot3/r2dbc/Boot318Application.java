package com.coding.boot3.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 3.x 对 R2dbc 的自动配置：
 * 1、R2dbcAutoConfiguration - 主要配置连接工厂、连接池
 * 2、R2dbcDataAutoConfiguration
 * a、r2dbcEntityTemplate - 操作数据库的响应式客户端
 * b、数据类型映射关系、转换器
 * 3、R2dbcRepositoriesAutoConfiguration - 开启 SpringData 声明式接口方式的CRUD
 * 4、R2dbcTransactionManagerAutoConfiguration - 事务管理
 * 5、R2dbcProxyAutoConfiguration - 代理
 */
@SpringBootApplication
public class Boot318Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot318Application.class, args);
    }

}