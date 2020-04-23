package com.yuntian.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author guangleilei.
 * @date Created in 16:43 2019/11/19
 * @description
 */
@Component
public class RedisLockUtil implements DistributedLock {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private static final String LOCK_KEY_PRE = "redisLock:";

    /**
     * 锁过期时间 30s
     */
    private final static long INTERNAL_LOCK_LEASE_TIME = 30;

    private static final Long RELEASE_SUCCESS = 1L;


    @Resource
    private DefaultRedisScript<Boolean> redisLockScript;


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void lock(String lockKey, String requestId) {
        for (; ; ) {
            Boolean locked = tryLock(getKey(lockKey), requestId, INTERNAL_LOCK_LEASE_TIME);
            if (locked!=null) {
                logger.info("获得锁:" + Thread.currentThread() + "线程" + ",标识：" + requestId);
                return;
            } else {
                logger.error("处于等待:" + Thread.currentThread() + "线程" + ",标识：" + requestId);
            }
        }
    }

    private String getKey(String lockKey) {
        return LOCK_KEY_PRE + lockKey;
    }


    public Boolean tryLock(String lockKey, String requestId, long exTime) {
        return stringRedisTemplate.opsForValue().setIfAbsent(lockKey, requestId, exTime, TimeUnit.SECONDS);
    }


    @Override
    public Boolean unLock(String lockKey, String requestId) {
        Object result = stringRedisTemplate.execute(redisLockScript, Collections.singletonList(getKey(lockKey)),requestId);
        logger.error("释放锁:" + Thread.currentThread() + "线程" + ",标识：" + requestId);
        return RELEASE_SUCCESS.equals(result);

    }
}
