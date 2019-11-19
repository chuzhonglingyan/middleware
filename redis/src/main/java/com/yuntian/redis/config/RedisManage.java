package com.yuntian.redis.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

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

    public <V> void set(String key, V v,long time) {
        getValueOperations().set(key,v,time,TimeUnit.SECONDS);
    }



    private <V> ValueOperations<String, V> getValueOperations() {
        return redisTemplate.opsForValue();
    }


}
