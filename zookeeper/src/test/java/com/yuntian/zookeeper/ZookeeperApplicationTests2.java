package com.yuntian.zookeeper;

import com.yuntian.zookeeper.client.DistributedQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ZookeeperApplicationTests2 {

    @Resource
    private DistributedQueue<String> distributedQueue;

    @Test
    public void contextLoads() {
        for (int i = 0; i < 10; i++) {
            distributedQueue.offer("商品"+(i+1));
        }
        System.out.println(distributedQueue.peek());
    }


}
