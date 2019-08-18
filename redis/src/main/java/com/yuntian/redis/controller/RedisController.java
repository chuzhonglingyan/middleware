package com.yuntian.redis.controller;

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

    @GetMapping("getValue")
    public Result getKey(String key) {
        Result result = new Result();
        result.setData(redisManage.getValue(key));
        result.setCode(99);
        return result;
    }


}
