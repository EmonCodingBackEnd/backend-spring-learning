package com.coding.reactor.r2dbc;

import com.coding.reactor.r2dbc.entity.TAuthor;
import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

public class R2DBCTests {

    /**
     * 思想：
     * 1、有了r2dbc，我们的应用在数据库层面天然支持高并发、高吞吐量
     * 2、并不能提升开发效率
     */
    @Test
    void connection() throws IOException {
        // 1、获取连接工厂；注意：查询字符串必须进行URL编码
        // 创建连接工厂方式一
        MySqlConnectionConfiguration configuration = MySqlConnectionConfiguration.builder()
                .host("localhost")
                .port(3306)
                .user("root")
                .password("root123")
                .database("r2dbc")
                .createDatabaseIfNotExist(true)
                .connectTimeout(Duration.ofSeconds(3))
                .build();
        ConnectionFactory connectionFactory = MySqlConnectionFactory.from(configuration);

        // 创建连接工厂方式二
        // ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:mysql://root:root123@127.0.0.1:3306/r2dbc");


        // 2、获取到连接，发送sql
        Mono<Connection> connectionMono = Mono.from(connectionFactory.create());

        connectionMono.flatMapMany(connection -> connection.createStatement("SELECT * FROM t_author where id=?").bind(0, 1L).execute())
                .flatMap(result -> result.map((row, rowMetadata) -> {
                            TAuthor tAuthor = new TAuthor();
                            tAuthor.setId(row.get("id", Long.class));
                            tAuthor.setName(row.get("name", String.class));
                            return tAuthor;
                        })
                ).subscribe(author -> System.out.println("author = " + author));

        System.in.read();
    }


    @Test
    void namedQuery() throws IOException {
        // 1、获取连接工厂；注意：查询字符串必须进行URL编码
        ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:mysql://root:root123@127.0.0.1:3306/r2dbc");


        // 2、获取到连接，发送sql
        Mono<Connection> connectionMono = Mono.from(connectionFactory.create());

        connectionMono.flatMapMany(connection ->
                        connection.createStatement("SELECT * FROM t_author where id=?id and name=?name ").bind("id", 1L).bind("name", "张三").execute())
                .flatMap(result -> result.map((row, rowMetadata) -> {
                            TAuthor tAuthor = new TAuthor();
                            tAuthor.setId(row.get("id", Long.class));
                            tAuthor.setName(row.get("name", String.class));
                            return tAuthor;
                        })
                ).subscribe(author -> System.out.println("author = " + author));

        System.in.read();
    }

}
