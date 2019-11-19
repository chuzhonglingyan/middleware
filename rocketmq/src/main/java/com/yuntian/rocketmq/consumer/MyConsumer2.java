package com.yuntian.rocketmq.consumer;

import com.yuntian.rocketmq.model.OrderPaidEvent;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: yuntian
 * @Date: 2019/10/24 0024 00:00
 * @Description:
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
public class MyConsumer2 implements RocketMQListener<OrderPaidEvent> {
    @Override
    public void onMessage(OrderPaidEvent orderPaidEvent) {
        log.info("received orderPaidEvent: {}", orderPaidEvent);
    }
}