package com.coding.springboot2.webadmin;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Junit5Test {

    @BeforeAll
    static void testBeforeAll() {
        // log.info("所有测试就要开始了...");
    }

    @AfterAll
    static void testAfterAll() {
        // log.info("所有测试就要结束了...");
    }

    @BeforeEach
    void testBeforeEach() {
        // log.info("测试就要开始了...");
    }

    @AfterEach
    void testAfterEach() {
        // log.info("测试就要结束了...");
    }

    @Autowired(required = false)
    JdbcTemplate jdbcTemplate;

    @Order(7)
    @Test
    public void test7() {
        log.info("7");
    }

    @Order(1)
    @Test
    @DisplayName("test1")
    public void test1() {
        log.info("1==>{}", jdbcTemplate);
    }

    @Order(3)
    @Test
    public void test3() throws InterruptedException {
        log.info("3");
        TimeUnit.SECONDS.sleep(1);
    }

    @Order(2)
    @Test
    public void test2() throws InterruptedException {
        log.info("2");
        TimeUnit.MILLISECONDS.sleep(500);
    }

    @Order(6)
    @Test
    @DisplayName("test6")
    public void test6() {
        log.info("6");
    }

    @Order(4)
    @RepeatedTest(3)
    public void test4() {
        log.info("4");
    }

    @Order(5)
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void test5() throws InterruptedException {
        log.info("5");
        TimeUnit.MILLISECONDS.sleep(300);
    }

    @Order(Integer.MIN_VALUE)
    @Test
    @Timeout(value = 510, unit = TimeUnit.MILLISECONDS)
    void testTimeoutOfFirst() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);
        log.info("testTimeout");
    }

    @Order(Integer.MAX_VALUE)
    @Test
    @Disabled
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testTimeoutOfLast() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);
        log.info("testTimeout");
    }

    @Test
    @DisplayName("测试简单断言")
    void testAssertionsSimple() {
        // 如果第一个错误，会阻断后续断言执行
        Assertions.assertEquals(5, 2 + 3);
        Assertions.assertNotSame(new Object(), new Object());
    }

    @Test
    @DisplayName("测试组合断言")
    void testAssertionsCombine() {
        // 如果第一个错误，并不会阻断后续断言的执行
        Assertions.assertAll("test", () -> Assertions.assertEquals(5, 2 + 3),
            () -> Assertions.assertNotSame(new Object(), new Object()));
    }

    @Test
    @DisplayName("测试异常断言")
    void testAssertionsException() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            int i = 10 / 0;
        });
    }

    @Test
    @DisplayName("测试超时断言")
    void testAssertionsTimeout() {
        Assertions.assertTimeout(Duration.ofMillis(500), () -> TimeUnit.MILLISECONDS.sleep(300));
    }

    @Test
    @DisplayName("测试快速失败断言")
    void testAssertionsFastFail() {
        Assertions.fail("不想执行了，快速失败吧");
    }

    @Test
    @DisplayName("测试前置条件")
    void testAssumptions() {
        Assumptions.assumeTrue(false, "如果不是true");
        log.info("测试前置条件");
    }
}
