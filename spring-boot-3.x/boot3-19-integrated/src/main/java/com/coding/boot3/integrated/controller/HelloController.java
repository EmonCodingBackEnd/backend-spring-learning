package com.coding.boot3.integrated.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("Hello World");
    }

    @PreAuthorize("hasAuthority('download')")
    @GetMapping("/world")
    public Mono<String> world() {
        return Mono.just("World");
    }

}
