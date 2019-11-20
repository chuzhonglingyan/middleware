package com.yuntian.redis;

import com.alibaba.fastjson.JSON;
import com.yuntian.redis.config.RedisManage;
import com.yuntian.redis.entity.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * https://gitee.com/whvse/RedisUtil
 */
@SpringBootTest
public class RedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    private RedisManage redisManage;

    @Test
    public void contextLoads() {

    }

    @Test
    public void set() {
        stringRedisTemplate.opsForValue().set("spring-boot", "测试");
        redisManage.set("test-x","22");
        redisManage.set("test-hh","22",10);

        User  user=new User();
        user.setId(3L);
        user.setName("王明");
        redisManage.set("userId_"+user.getId(),user);
    }

    @Test
    public void setAndGet() {
        redisManage.set("test-key1","测试入库");
        System.out.println(redisManage.getValue("test-key1")+"");

        stringRedisTemplate.opsForValue().set("test-key2","hhh");
        System.out.println(stringRedisTemplate.opsForValue().get("test-key2"));
    }

    @Test
    public void get() {
        String str = stringRedisTemplate.opsForValue().get("spring-boot");
        System.out.println(str);

        User  user=redisManage.getValue("userId_"+3);
        System.out.println(JSON.toJSON(user));


        User  userTest=new User();
        userTest.setId(19L);
        userTest.setName("小红");
        redisManage.set("userId:"+userTest.getId(),user);

        System.out.println(redisManage.getValue("redis_lock")+"");

    }


    /**
     * List（列表）   3
     * 5
     * 双向链表栈       2
     */
    @Test
    public void leftPush() {
        String key = "listLeft";
        stringRedisTemplate.delete(key);
        stringRedisTemplate.opsForList().leftPushAll(key, new String[]{"2", "5", "3"});
        //结果：3,5,2
        System.out.println(stringRedisTemplate.opsForList().range(key, 0, 1));
        System.out.println(stringRedisTemplate.opsForList().range(key, 0, -1));
        System.out.println(stringRedisTemplate.opsForList().leftPop(key));
        System.out.println(stringRedisTemplate.opsForList().range(key, 0, -1));
        System.out.println(stringRedisTemplate.opsForList().range(key, 0, -1));
    }

    /**
     * List（列表）
     * <p>
     * 双向链表队列
     */
    @Test
    public void rightPush() {
        String key = "listRight";
        stringRedisTemplate.delete(key);
        stringRedisTemplate.opsForList().rightPushAll(key, new String[]{"2", "5", "3"});
        System.out.println(stringRedisTemplate.opsForList().range(key, 0, 1));
        //结果：2,5,3
        System.out.println(stringRedisTemplate.opsForList().range(key, 0, -1));
        System.out.println(stringRedisTemplate.opsForList().rightPop(key));
        System.out.println(stringRedisTemplate.opsForList().range(key, 0, -1));
        System.out.println(stringRedisTemplate.opsForList().leftPop(key));
    }


}
