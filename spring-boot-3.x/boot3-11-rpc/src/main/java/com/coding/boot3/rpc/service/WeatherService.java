package com.coding.boot3.rpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

    @Autowired
    private WeatherInterface weatherInterface;

    public Mono<String> weather(String city) {
        // 远程调用阿里云API
//        return getByWebClient(city);
//        return getByHttpInterface(city);
        return weatherInterface.getWeather(city, "APPCODE e9966259394547938f494a414ad77aa2");
    }

    private static Mono<String> getByWebClient(String city) {
        // 1、创建WebClient
        WebClient client = WebClient.create();
        // 2、准备数据
        Map<String, String> params = new HashMap<>();
        // 3、定义发请求行为
        params.put("city", city);
        Mono<String> jsonMono = client.get().uri("https://jisuqgtq.market.alicloudapi.com/weather/query?city={city}", params) //
                .accept(MediaType.APPLICATION_JSON) // 定义响应的内容类型
                .header("Authorization", "APPCODE e9966259394547938f494a414ad77aa2") // 定义请求头
                .retrieve().bodyToMono(String.class);
        return jsonMono;
    }

    private static Mono<String> getByHttpInterface(String city) {
        Mono<String> jsonMono;
        // 1、创建客户端
        WebClient client = WebClient.builder().baseUrl("https://jisuqgtq.market.alicloudapi.com").codecs(clientCodecConfigurer -> {
            clientCodecConfigurer.defaultCodecs().maxInMemorySize((256 * 1024 * 1024)); // 响应数据量太大有可能会超出 BufferSize，所以这里设置的大一点。
        }).build();
        // 2、创建工厂
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        // 3、获取代理对象
        WeatherInterface weatherInterface = factory.createClient(WeatherInterface.class);
        jsonMono = weatherInterface.getWeather(city, "APPCODE e9966259394547938f494a414ad77aa2");
        return jsonMono;
    }
}
