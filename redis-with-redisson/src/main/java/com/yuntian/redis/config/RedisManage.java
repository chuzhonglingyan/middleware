package com.yuntian.redis.config;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/8/17 0017 22:17
 * @Description:
 */
public class RedisManage {


    @Resource
    private RedisTemplate redisTemplate;


    public <V> V getValue(String key) {
        ValueOperations<String, V> valueOperations = getValueOperations();
        return valueOperations.get(key);
    }

    public <V> void set(String key, V v) {
        getValueOperations().set(key, v);
    }

    public <V> void set(String key, V v, long time) {
        getValueOperations().set(key, v, time, TimeUnit.SECONDS);
    }


    private <V> ValueOperations<String, V> getValueOperations() {
        return redisTemplate.opsForValue();
    }

    /**
     * 元素入集合
     *
     * @param key
     * @param v
     * @param time
     * @param <V>
     */
    public <V> void push(String key, V v, long time) {
        List<V> list = new ArrayList<>();
        list.add(v);
        push(key, list, time);
    }

    /**
     * 元素入集合
     *
     * @param key
     * @param v
     * @param time
     * @param <V>
     */
    public <V> void push(String key, List<V> v, long time) {
        getListOperations().rightPushAll(key, v, time);
    }

    /**
     * 元素出栈
     *
     * @param key
     * @param <V>
     */
    public <V> void pop(String key) {
        getListOperations().rightPop(key);
    }

    /**
     * 元素出队列
     *
     * @param key
     * @param <V>
     */
    public <V> void peek(String key) {
        getListOperations().leftPop(key);
    }


    /**
     * List（列表） Redis的List是链表型的数据结构，可以使用LPUSH/RPUSH/LPOP/RPOP等命令在List的两端执行插入元素和弹出元素的操作
     * 两端执行 时间复杂度O(1)
     * 特定index上插入和读取元素的功能，但其时间复杂度较高O(N)
     */
    private <V> ListOperations<String, V> getListOperations() {
        return redisTemplate.opsForList();
    }


}
