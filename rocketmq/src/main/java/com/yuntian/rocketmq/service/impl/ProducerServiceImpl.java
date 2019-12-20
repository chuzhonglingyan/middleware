package com.yuntian.rocketmq.service.impl;

import com.yuntian.rocketmq.service.ProducerService;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author guangleilei.
 * @date Created in 10:58 2019/11/19
 * @description
 */
@Slf4j
@Service
public class ProducerServiceImpl implements ProducerService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    @Override
    public void send(String destination, Serializable msg) {
        try {
            long time = System.currentTimeMillis();
            rocketMQTemplate.send(destination, MessageBuilder.withPayload(msg).build());
            log.info("消息发送完成," + (System.currentTimeMillis() - time) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在发送客户端发送事务性消息并且实现回查Listener
     *
     * @param txProducerGroup
     * @param destination
     * @param msg
     * @return
     */
    @Override
    public TransactionSendResult sendMessageInTransaction(String txProducerGroup, String destination, Serializable msg) {
        return rocketMQTemplate.sendMessageInTransaction(txProducerGroup, destination, MessageBuilder.withPayload(msg).build(), null);
    }


    @Override
    public void syncSend(String destination, Serializable msg) {
        try {
            long time = System.currentTimeMillis();
            SendResult sendResult = rocketMQTemplate.syncSend(destination, MessageBuilder.withPayload(msg).build(), 60000);
            log.info("消息发送完成," + (System.currentTimeMillis() - time) + "ms" + ",sendResult=" + sendResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
