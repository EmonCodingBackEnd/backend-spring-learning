package com.coding.boot3.rpc.config;

import com.coding.boot3.rpc.service.WeatherInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WeatherConfiguration {

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory() {
        // 1、创建客户端
        WebClient client = WebClient.builder()
                .codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs()
                            .maxInMemorySize((256 * 1024 * 1024)); // 响应数据量太大有可能会超出 BufferSize，所以这里设置的大一点。
                }).build();
        // 2、创建工厂
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory;
    }

    @Bean
    public WeatherInterface weatherInterface(HttpServiceProxyFactory factory) {
        // 3、获取代理对象
        WeatherInterface weatherInterface = factory.createClient(WeatherInterface.class);
        return weatherInterface;
    }
}
