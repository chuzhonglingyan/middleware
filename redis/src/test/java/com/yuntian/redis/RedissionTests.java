package com.yuntian.redis;


import com.yuntian.redis.lock.RedissLockUtil;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * https://gitee.com/whvse/RedisUtil
 */
@SpringBootTest
@Slf4j
public class RedissionTests {


    @Test
    public void contextLoads() {

    }

    private int count;


    @Test
    public void testNoLock() throws InterruptedException {
        int clientcount = 100;
        CountDownLatch countDownLatch = new CountDownLatch(clientcount);
        ExecutorService executorService = Executors.newFixedThreadPool(clientcount);
        long start = System.currentTimeMillis();
        for (int i = 0; i < clientcount; i++) {
            executorService.execute(() -> {
                try {
                    log.info(Thread.currentThread()+"进入");
                    Thread.sleep(300);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    log.info(Thread.currentThread()+"离开");
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        log.info("执行线程数:{},总耗时:{},count数为:{}", clientcount, end - start+"ms", count);
    }



    @Test
    public void testLock() throws InterruptedException {
        String lockKey = "redis_lock";
        int clientcount = 100;
        CountDownLatch countDownLatch = new CountDownLatch(clientcount);
        ExecutorService executorService = Executors.newFixedThreadPool(clientcount);
        long start = System.currentTimeMillis();
        for (int i = 0; i < clientcount; i++) {
            executorService.execute(() -> {
                try {
                    log.info(Thread.currentThread()+"进入");
                    RedissLockUtil.lock(lockKey,30);
                    log.info(Thread.currentThread()+"正在运行");
                    Thread.sleep(200);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    RedissLockUtil.unlock(lockKey);
                    log.info(Thread.currentThread()+"离开");
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        log.info("执行线程数:{},总耗时:{},count数为:{}", clientcount, end - start+"ms", count);
    }


}
