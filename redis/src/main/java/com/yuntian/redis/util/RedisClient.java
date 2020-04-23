package com.yuntian.redis.util;

/**
 * @author guangleilei.
 * @date Created in 16:01 2019/11/13
 * @description
 */

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类类
 *
 * @author zhangxinlin
 */
public class RedisClient {

    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public <V> void set(String key, V val) {
        ValueOperations<String, V> ops = redisTemplate.opsForValue();
        ops.set(key, val);
    }

    public <V> void set(String key, V val, long expireSecond) throws Exception {
        ValueOperations<String, V> ops = redisTemplate.opsForValue();
        ops.set(key, val, expireSecond, TimeUnit.SECONDS);
    }

    public <V> V get(String key) {
        ValueOperations<String, V> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }


    public <V> void addZSet(String key, Set<ZSetOperations.TypedTuple<V>> tuples) {
        ZSetOperations<String, V> ops = redisTemplate.opsForZSet();
        ops.add(key, tuples);
    }

    /**
     * 从小到大排列
     *
     * @param key
     * @param o
     * @param <V>
     * @return
     */
    public <V> Long rank(String key, V o) {
        ZSetOperations<String, V> ops = redisTemplate.opsForZSet();
        Long rank = ops.rank(key, o);
        return rank == null ? 0 : rank + 1;
    }

    /**
     * 从大到小排列
     *
     * @param key
     * @param o
     * @param <V>
     * @return
     */
    public <V> Long reverseRank(String key, V o) {
        ZSetOperations<String, V> ops = redisTemplate.opsForZSet();
        Long rank = ops.reverseRank(key, o);
        return rank == null ? 0 : rank + 1;
    }

    /**
     * 获取前n名的排行榜数据
     * @param n
     * @return
     */
    public <V> Set<V> getTopRanks(String key, int n) {
        ZSetOperations<String, V> ops = redisTemplate.opsForZSet();
        return ops.range(key, 0, n - 1);
    }


    public <V> Boolean setIfAbsent(String key, V val, long expireSecond) {
        ValueOperations<String, V> ops = redisTemplate.opsForValue();
        return ops.setIfAbsent(key, val, expireSecond, TimeUnit.SECONDS);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }


    public void count(String key, Double count) {
        redisTemplate.opsForValue().increment(key, count);
    }


}
