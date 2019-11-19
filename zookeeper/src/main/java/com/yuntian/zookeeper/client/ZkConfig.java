package com.yuntian.zookeeper.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/8/17 0017 22:14
 * @Description:
 */
@Configuration
public class ZkConfig {

    @Bean
    DistributedQueue<String> stringDistributedQueue(ZKCustor zkCustor) {
        return new DistributedQueue<>(zkCustor,"good");
    }



}
