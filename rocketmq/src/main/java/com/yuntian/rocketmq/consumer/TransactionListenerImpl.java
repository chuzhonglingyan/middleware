package com.yuntian.rocketmq.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @author guangleilei.
 * @date Created in 15:03 2019/11/19
 * @description可以不用为每个 @RocketMQTransactionListener 注解配置 AK/SK，
 * 在配置文件中配置 rocketmq.producer.access-key 和 rocketmq.producer.secret-key 配置项，这两个配置项的值就是默认值
 */
@RocketMQTransactionListener(txProducerGroup = "test")
class TransactionListenerImpl implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // ... local transaction process, return bollback, commit or unknown
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // ... check transaction status and return bollback, commit or unknown
        return RocketMQLocalTransactionState.COMMIT;
    }
}