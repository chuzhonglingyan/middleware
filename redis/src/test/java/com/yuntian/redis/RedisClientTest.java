package com.yuntian.redis;

import com.yuntian.redis.entity.Article;
import com.yuntian.redis.entity.UserInfo;
import com.yuntian.redis.util.RedisClient;
import com.yuntian.redis.util.RedisStack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author guangleilei.
 * @date Created in 16:09 2019/11/13
 * @description https://www.jianshu.com/p/a43c6d2f8bfb
 */
@Slf4j
@SpringBootTest
public class RedisClientTest {

    @Autowired
    RedisClient redisClient;


    @Test
    void testRedis() {
        redisClient.set("testKey", "hh");

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setName("哈哈");
        String userKey = "user:" + userInfo.getId();
        redisClient.set(userKey, userInfo);

        System.out.println("从redis获取的key值：" + redisClient.get("testKey"));
        System.out.println("从redis获取的key值：" + redisClient.get(userKey));
    }

    @Test
    void testAddZSet() {
        String key = "user:score_list";
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            DefaultTypedTuple<String> defaultTypedTuple = new DefaultTypedTuple<>("100-"+i, new Random().nextDouble() * 100);
            set.add(defaultTypedTuple);
        }
        redisClient.addZSet(key, set);
        System.out.println("获取顺序排名:第" + (redisClient.rank(key, "100-"+"1")));

        System.out.println("获取倒序排名:第" + (redisClient.reverseRank(key, "100-"+"1")));

        System.out.println("获取topN:" + redisClient.getTopRanks(key,5).toString());
    }

    @Test
    void testUserFav() {
        Article article = new Article(1L, 0L);
        String key = "article:likeCount:" + article.getId();
        for (int i = 0; i < 10; i++) {
            redisClient.count(key, 1d);
        }
        System.out.println("总点赞数：" + redisClient.get(key));
    }

    @Resource
    private RedisStack redisStack;

    @Test
    void testUserStack() {
        String listKey="nums";
        redisStack.push(listKey, 1,2,3);
        System.out.println(redisStack.getAll(listKey));
        redisStack.pop(listKey);
        System.out.println(redisStack.getAll(listKey));
    }


}
