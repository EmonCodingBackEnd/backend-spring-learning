package com.coding.boot3.rpc.service;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

public interface WeatherInterface {

    @GetExchange(url = "https://jisuqgtq.market.alicloudapi.com/weather/query", accept = "application/json")
    Mono<String> getWeather(@RequestParam("city") String city, @RequestHeader("Authorization") String auth);
}
