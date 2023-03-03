package com.coding.springboot2.webadmin;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisplayName("参数化测试")
public class Junit5ParameterizedTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testParameterized(int i) {
        log.info("==>{}", i);
    }

    static Stream<String> stringProvider() {
        return Stream.of("apple", "banana", "orange");
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void testParameterized(String i) {
        log.info("==>{}", i);
    }
}
