package com.coding.boot3.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Map;

@SpringBootTest
class Boot309ApplicationTests {

    @Autowired // key,value都必须是字符串
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
    }

    /**
     * string: 普通字符串   redisTemplate.opsForValue().set("key", "value");
     */
    @Test
    void testString() {
        stringRedisTemplate.opsForValue().set("string", "问秋");
        String name = stringRedisTemplate.opsForValue().get("string");
        System.out.println(name);
    }

    /**
     * list:   列表        redisTemplate.opsForList().leftPush("list", "value");
     */
    @Test
    void testList() {
        String listName = "list";
        stringRedisTemplate.opsForList().leftPush(listName, "1");
        stringRedisTemplate.opsForList().leftPush(listName, "2");
        stringRedisTemplate.opsForList().leftPush(listName, "3");
        stringRedisTemplate.opsForList().leftPush(listName, "4");
        String pop = stringRedisTemplate.opsForList().leftPop(listName);
        System.out.println(pop);
    }

    /**
     * set:    集合        redisTemplate.opsForSet().add("set", "value");
     */
    @Test
    void testSet() {
        String setName = "set";
        stringRedisTemplate.opsForSet().add(setName, "1", "2", "3", "3");
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(setName, "1");
        Assertions.assertTrue(isMember);
        Boolean isMember2 = stringRedisTemplate.opsForSet().isMember(setName, "5");
        Assertions.assertFalse(isMember2);
    }

    /**
     * zset:   有序集合     redisTemplate.opsForZSet().add("zset", "value", 1);
     */
    @Test
    void testZSet() {
        String zsetName = "zset";
        stringRedisTemplate.opsForZSet().add(zsetName, "问秋", 90.00);
        stringRedisTemplate.opsForZSet().add(zsetName, "张三", 85);
        stringRedisTemplate.opsForZSet().add(zsetName, "李四", 9.00);
        stringRedisTemplate.opsForZSet().add(zsetName, "王五", 96.00);
        ZSetOperations.TypedTuple<String> stringTypedTuple = stringRedisTemplate.opsForZSet().popMax(zsetName);
        String value = stringTypedTuple.getValue();
        Double score = stringTypedTuple.getScore();
        System.out.println(value + "=" + score);
    }


    /**
     * hash:   散列        redisTemplate.opsForHash().put("hash", "key", "value");
     */
    @Test
    void testHash() {
        String hashName = "hash";
        stringRedisTemplate.opsForHash().put(hashName, "name", "问秋");
        stringRedisTemplate.opsForHash().put(hashName, "age", "18");
    }
}
