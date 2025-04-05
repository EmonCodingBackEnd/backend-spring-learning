package com.coding.boot3.r2dbc.config;

import com.coding.boot3.r2dbc.converter.BookReadConverter;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories // 启用R2DBC仓库
@Configuration
public class R2dbcConfiguration {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(ConnectionFactory connectionFactory) {
        // return R2dbcCustomConversions.of(MySqlDialect.INSTANCE, new BookReadConverter());
        R2dbcDialect dialect = DialectResolver.getDialect(connectionFactory);
        // 假如自定义的转换器，需要知道当前数据库的方言；效果是：新增了我们自定义的转换器，保留了之前的转换器
        return R2dbcCustomConversions.of(dialect, new BookReadConverter());
    }
}
