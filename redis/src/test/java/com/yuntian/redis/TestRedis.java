package com.yuntian.redis;

import com.yuntian.redis.service.RedisLuaService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yuntian
 * @date 2020/3/18 0018 11:33
 * @description
 */
@Slf4j
@SpringBootTest
public class TestRedis {

    @Resource
    private RedisLuaService redisLuaService;

    @Test
    void testRedis() {
        String ip = "168.56.223.456";
        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(() -> {
                if (finalI < 50) {
                    try {
                        Thread.sleep(new Random().nextInt(500) + 500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(new Random().nextInt(1000) + 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                boolean pass = redisLuaService.rateLimit(ip, 10, 1);
                if (pass) {
                    System.out.println("执行通过:" + LocalDateTime.now());
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行完毕" + Thread.currentThread() + "耗时：" + (System.currentTimeMillis() - startTime) + "ms");

    }
}
