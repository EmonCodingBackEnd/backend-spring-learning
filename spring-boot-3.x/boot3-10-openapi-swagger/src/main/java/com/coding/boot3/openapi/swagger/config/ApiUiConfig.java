package com.coding.boot3.openapi.swagger.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiUiConfig {

    /**
     * 分组设置
     *
     * @return
     */
    @Bean
    public GroupedOpenApi empApi() {
        return GroupedOpenApi.builder()
                .group("员工管理")
                .pathsToMatch("/emp/**", "/emps")
                .build();
    }

    @Bean
    public GroupedOpenApi deptApi() {
        return GroupedOpenApi.builder()
                .group("部门管理")
                .pathsToMatch("/dept/**", "/depts")
                .addOpenApiMethodFilter(method -> method.getName().startsWith("get"))
                .build();
    }

    @Bean
    public OpenAPI docOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SpringBoot3-CRUD API")
                        .description("专门测试接口文档")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("springlearning Wiki Documentation")
                        .url("https://springlearning.wiki.github.org/docs"));
    }
}
