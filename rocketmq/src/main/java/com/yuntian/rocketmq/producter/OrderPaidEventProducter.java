package com.yuntian.rocketmq.producter;

import com.yuntian.rocketmq.event.OrderPaidEvent;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/10/23 0023 23:39
 * @Description:
 */
@Slf4j
@Component
public class OrderPaidEventProducter {

    @Resource
    private RocketMQTemplate rocketmqTemplate;


    public void send(String topic,String name) {
        rocketmqTemplate.convertAndSend(topic, name);
        log.info("发送成功...");
    }

    public void send(String topic, OrderPaidEvent orderPaidEvent) {
        rocketmqTemplate.send(topic, MessageBuilder.withPayload(orderPaidEvent).build());
        log.info("发送成功...");
    }
}
