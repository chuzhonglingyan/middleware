package com.yuntian.redis.controller;

import com.yuntian.redis.aspect.LimitVerification;
import com.yuntian.redis.aspect.TimeTrace;
import com.yuntian.redis.common.Result;
import com.yuntian.redis.config.RedisManage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/8/18 0018 13:43
 * @Description:
 */
@RestController
@RequestMapping("redis")
public class RedisController {

    @Resource
    private RedisManage redisManage;

    @TimeTrace
    @GetMapping("getValue")
    public Result getKey(String key) {
        Result result = new Result();
        result.setData(redisManage.getValue(key));
        result.setCode(99);
        return result;
    }

    @LimitVerification(key = "127.0.0.1", times = 5, expire = 10)
    @GetMapping("testLimit")
    public Result testLimit(String name) {
        Result result = new Result();
        result.setData(redisManage.getValue(name));
        result.setCode(99);
        return result;
    }
}
