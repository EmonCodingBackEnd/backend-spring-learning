package com.coding.boot3.r2dbc.controller;

import com.coding.boot3.r2dbc.entity.TAuthor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AuthorController {

    @GetMapping("/authors")
    public Flux<TAuthor> getAllAuthor() {
        return Flux.just(new TAuthor(1L, "author1"), new TAuthor(2L, "author2"));
    }
}
