package com.yuntian.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: yuntian
 * @Date: 2019/11/20 0020 23:04
 * @Description: 适合存储大对象，修改少量属性，减少了序列化/反序列化的开销
 * Redis提供了接口(hgetall)可以直接取到全部的属性数据,但是如果内部Map的成员很多，那么涉及到遍历整个内部Map的操作，
 * 由于Redis单线程模型的缘故，这个遍历操作可能会比较耗时，而另其它客户端的请求完全不响应，这点需要格外注意。
 */
@SpringBootTest
public class RedisHashTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void opsForHashPut() {
        String userId1="userId:1001";
        stringRedisTemplate.opsForHash().put(userId1, "name", "baipengfei");
        stringRedisTemplate.opsForHash().put(userId1, "age", "22");
        stringRedisTemplate.opsForHash().put(userId1, "height", "176");

        String userId2="userId:1002";
        Map<String, String> data = new HashMap<>();
        data.put("name", "jack ma");
        data.put("company", "alibaba");
        data.put("age", "500");
        stringRedisTemplate.opsForHash().putAll(userId2, data);
    }

    @Test
    public void opsForHashGet() {
        String userId1="userId:1001";
        System.out.println(stringRedisTemplate.opsForHash().get(userId1, "name"));

        //multiGet方法，根据key和多个hashkey找出对应的多个值
        Collection<Object> keys = new ArrayList<>();
        keys.add("name");
        keys.add("age");
        System.out.println(stringRedisTemplate.opsForHash().multiGet(userId1, keys));
    }




}
