package com.yuntian.zookeeper.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/8/18 0018 17:10
 * @Description:
 */
@Component
public class ZKProperty {

    @Value("${zk.server}")
    private String zkServer;

    @Value("${zk.namespace}")
    private String namespace;


    @Value("${zk.sessionTimeout}")
    private Integer sessionTimeout;

    @Value("${zk.baseSleepTimeMs}")
    private Integer baseSleepTimeMs;

    @Value("${zk.maxRetries}")
    private Integer maxRetries;


    public String getZkServer() {
        return zkServer;
    }

    public void setZkServer(String zkServer) {
        this.zkServer = zkServer;
    }

    public Integer getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Integer getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(Integer baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
