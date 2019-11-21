package com.yuntian.redis.service;

/**
 * @Auther: yuntian
 * @Date: 2019/11/22 0022 00:04
 * @Description:
 */
public interface RedisLuaService {

    /**
     * @param key  限制条件
     * @param times  次数
     * @param expire 时间段
     * @return
     */
    Boolean rateLimit(String key,Integer times, Integer expire);
}
