package com.yuntian.redis.util;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

/**
 * @author yuntian
 * @date 2020/4/3 0003 18:53
 * @description
 */
@Component
public class RedisQueue {

    @Resource
    private RedisTemplate redisTemplate;

    private final static String PRE = "queue:";

    private String getKey(String key) {
        return String.join(":", PRE, key);
    }


    public <V> ListOperations<String, V> getOp() {
        return redisTemplate.opsForList();
    }

    /**
     * 入队列
     *
     * @param key
     * @param <V>
     */
    public <V> void offer(String key, V... values) {
        getOp().rightPushAll(getKey(key), values);
    }


    /**
     * 元素出队列
     *
     * @param key
     * @param <V>
     */
    public <V> void take(String key) {
        getOp().leftPop(getKey(key));
    }

    public <V> List<V> getAll(String key) {
        return (List<V>) getOp().range(getKey(key), 0, -1);
    }


}
