package com.yuntian.rocketmq;

import com.yuntian.rocketmq.event.OrderPaidEvent;
import com.yuntian.rocketmq.producter.OrderPaidEventProducter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import javax.annotation.Resource;

@SpringBootTest
class RocketmqApplicationTests {


    @Resource
    private OrderPaidEventProducter orderPaidEventProducter;



    @Test
    void contextLoads() {
        orderPaidEventProducter.send("test-topic-2", new OrderPaidEvent("aa,22",new BigDecimal(22)));
    }

}
