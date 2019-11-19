package com.yuntian.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * mqbroker.exe -n localhost:9876 autoCreateTopicEnable=true
 */
@SpringBootApplication
public class RocketmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqApplication.class, args);
    }

}
