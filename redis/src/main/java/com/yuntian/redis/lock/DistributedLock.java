package com.yuntian.redis.lock;

/**
 * @author guangleilei.
 * @date Created in 17:20 2019/11/19
 * @description
 */
public interface DistributedLock {


    void   lock(String lockKey, String requestId);



    Boolean  unLock(String lockKey, String requestId);

}
