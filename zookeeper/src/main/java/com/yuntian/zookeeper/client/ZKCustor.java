package com.yuntian.zookeeper.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: yuntian
 * @Date: 2019/8/18 0018 17:08
 * @Description:
 */
@Slf4j
@Component
public class ZKCustor {

    private CuratorFramework client = null;

    @Resource
    private ZKProperty zkProperty;

    @PostConstruct
    public void init() {
        initClient();
    }

    public CuratorFramework getClient() {
        return client;
    }

    private void initClient() {
        if (client != null) {
            return;
        }
        //创建重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkProperty.getBaseSleepTimeMs(), zkProperty.getMaxRetries());
        //创建zookeeper客户端
        client = CuratorFrameworkFactory.builder().connectString(zkProperty.getZkServer())
                .sessionTimeoutMs(zkProperty.getSessionTimeout())
                .retryPolicy(retryPolicy)
                .namespace(zkProperty.getNamespace())
                .build();
        client.start();
    }

    public void createNode(String path) {
        createNode(path, null);
    }

    public void createNode(String path, String data) {
        if (client.getState() == CuratorFrameworkState.STARTED) {
            try {
                if (client.checkExists().forPath(path) == null) {
                    if (data != null) {
                        client.create().creatingParentContainersIfNeeded()
                                .withMode(CreateMode.PERSISTENT)
                                .forPath(path,data.getBytes(StandardCharsets.UTF_8));
                    }else {
                        client.create().creatingParentContainersIfNeeded()
                                .withMode(CreateMode.PERSISTENT)
                                .forPath(path);
                    }
                    log.info("节点：" + path + ",创建成功");
                } else {
                    log.info("节点：" + path + "已存在");
                }
            } catch (Exception e) {
                log.error("zkClient初始化失败" + "," + e.getMessage());
            }
        } else {
            log.error("zkClient还未初始化");
        }
    }

    public boolean deleteNode(String path) {
        try {
            if (client.checkExists().forPath(path) != null) {
                client.delete().deletingChildrenIfNeeded().forPath(path);
                log.info("节点：" + path + ",删除成功");
                return true;
            } else {
                log.info("节点：" + path + "不存在");
            }
        } catch (Exception e) {
            log.error("zkClient初始化失败" + "," + e.getMessage());
        }
        return false;
    }


    public String getNodeData(String path) {
        try {
            if (client.checkExists().forPath(path) != null) {
                String data = new String(client.getData().forPath(path), StandardCharsets.UTF_8);
                log.info("节点：" + path + ",数据：" + data);
                return data;
            } else {
                log.info("节点：" + path + "不存在");
            }
        } catch (Exception e) {
            log.error("zkClient初始化失败" + "," + e.getMessage());
        }
        return "";
    }

    public void setNodeData(String path, String data) {
        try {
            if (client.checkExists().forPath(path) != null) {
                client.setData().forPath(path, data.getBytes(StandardCharsets.UTF_8));
                log.info("修改节点：" + path + ",数据：" + data);
            } else {
                log.info("节点：" + path + "不存在");
            }
        } catch (Exception e) {
            log.error("zkClient初始化失败" + "," + e.getMessage());
        }
    }

    public void addTreeCacheListener(String path) {
        try {
            TreeCache cache = new TreeCache(client, path);
            cache.start();
            //添加错误监听器
            cache.getUnhandledErrorListenable().addListener((s, throwable) -> log.error("======>  错误原因：" + throwable.getMessage()));
            //节点变化的监听器
            cache.getListenable().addListener((curatorFramework, treeCacheEvent) -> {
                switch (treeCacheEvent.getType()) {
                    case INITIALIZED:
                        log.info("=====> INITIALIZED :  初始化");
                        break;
                    case NODE_ADDED:
                        log.info("=====> NODE_ADDED : " + treeCacheEvent.getData().getPath() + "  数据:" + new String(treeCacheEvent.getData().getData()));
                        break;
                    case NODE_REMOVED:
                        log.info("=====> NODE_REMOVED : " + treeCacheEvent.getData().getPath() + "  数据:" + new String(treeCacheEvent.getData().getData()));
                        break;
                    case NODE_UPDATED:
                        log.info("=====> NODE_UPDATED : " + treeCacheEvent.getData().getPath() + "  数据:" + new String(treeCacheEvent.getData().getData()));
                        break;
                    default:
                        log.info("=====> treeCache Type:" + treeCacheEvent.getType());
                        break;
                }
            });
        } catch (Exception e) {
            log.error("zkClient初始化失败" + "," + e.getMessage());
        }
    }

    public void close() {
        client.close();
    }

}
