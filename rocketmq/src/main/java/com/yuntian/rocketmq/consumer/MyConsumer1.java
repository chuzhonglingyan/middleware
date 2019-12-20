package com.yuntian.rocketmq.consumer;

import com.yuntian.rocketmq.constant.Topic;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author guangleilei.
 * @date Created in 10:55 2019/11/19
 * @description
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = Topic.TEST_TOPIC_1, consumerGroup = "my-consumer_"+Topic.TEST_TOPIC_1)
public class MyConsumer1 implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("received message: {}", message);
    }
}

