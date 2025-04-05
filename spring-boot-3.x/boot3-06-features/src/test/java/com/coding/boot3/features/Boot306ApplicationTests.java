package com.coding.boot3.features;

import com.coding.boot3.features.service.HelloService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

// 测试类也必须在主程序所在的包及其子包
@SpringBootTest // 这个注解让该测试类具有测试SpringBoot应用容器中所有组件的功能
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Boot306ApplicationTests {

    @Autowired
    private HelloService helloService;

    @BeforeAll // 所有测试方法运行之前先运行这个
    static void initAll() {
        System.out.println("hello");
    }

    @BeforeEach
        // 每个测试方法运行之前先运行这个
    void init() {
        System.out.println("world");
    }


    @Test
    @Order(1)
    void contextLoads() {
        int sum = helloService.sum(1, 2);
        Assertions.assertEquals(3, sum);
    }

    @ParameterizedTest
    @ValueSource(strings = {"one", "two", "three"})
    @DisplayName("参数化测试1")
    public void parameterizedTest1(String string) {
        System.out.println(string);
        Assertions.assertTrue(StringUtils.isNotBlank(string));
    }

    @ParameterizedTest
    @MethodSource("method")    //指定方法名
    @DisplayName("方法来源参数")
    public void testWithExplicitLocalMethodSource(String name) {
        System.out.println(name);
        Assertions.assertNotNull(name);
    }

    // 返回Stream即可
    static Stream<String> method() {
        return Stream.of("apple", "banana");
    }

    @DisplayName("😱")
    @Test
    @Order(2)
    void succeedingTest() {
    }

    @Test
    @Order(3)
    void failingTest() {
        fail("a failing test");
    }

    @Test
    @Disabled("for demonstration purposes")
    @Order(4)
    void skippedTest() {
        // not executed
    }

    @Test
    @Order(5)
    void abortedTest() {
        assumeTrue("abc".contains("Z"));
        fail("test should have been aborted");
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }
}
