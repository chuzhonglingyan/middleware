package com.yuntian.rocketmq;

import com.yuntian.rocketmq.model.OrderPaidEvent;
import com.yuntian.rocketmq.producter.OrderPaidEventProducter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RocketmqApplicationTests {


    @Resource
    private OrderPaidEventProducter orderPaidEventProducter;



    @Test
    void contextLoads() {
        orderPaidEventProducter.send("test-topic-2", new OrderPaidEvent("aa,22",22));
    }

}
