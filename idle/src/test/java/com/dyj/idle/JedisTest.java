package com.dyj.idle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class JedisTest {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @Test
    void testString(){
//        String res=jedis.set("name","张三");
//        System.out.println(res);
//
//        String name=jedis.get("name");
//        System.out.println(name);
        // 获取 ValueOperations 对象
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

// 使用 ValueOperations 设置键值对和过期时间
        operations.set("email:verification:codess", "your_verification_codess", 10, TimeUnit.SECONDS);
//        stringRedisTemplate.opsForValue().set("a","忽略");
//        Object name=stringRedisTemplate.opsForValue().get("a");
//        System.out.println(name);
    }


}
