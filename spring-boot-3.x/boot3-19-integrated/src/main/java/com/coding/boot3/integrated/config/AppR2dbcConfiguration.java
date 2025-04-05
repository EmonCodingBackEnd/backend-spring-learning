package com.coding.boot3.integrated.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories // 启用R2DBC仓库
@Configuration
public class AppR2dbcConfiguration {

}
