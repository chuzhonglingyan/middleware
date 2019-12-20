package com.yuntian.rocketmq.service;

import org.apache.rocketmq.client.producer.TransactionSendResult;

import java.io.Serializable;

/**
 * @author guangleilei.
 * @date Created in 10:58 2019/11/19
 * @description
 */
public interface ProducerService {

    void  send(String destination, Serializable msg);


    TransactionSendResult sendMessageInTransaction(final String txProducerGroup, String destination, Serializable msg);

    void  syncSend(String destination, Serializable msg);
}
