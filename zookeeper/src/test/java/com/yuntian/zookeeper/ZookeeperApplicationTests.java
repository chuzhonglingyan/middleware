package com.yuntian.zookeeper;

import com.yuntian.zookeeper.client.ZKCustor;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ZookeeperApplicationTests {

    @Resource
    private ZKCustor zkCustor;

    @Test
    public void contextLoads() {
    }


    @Test
    public void createNode() {
        zkCustor.createNode("/test");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        zkCustor.createNode("/test/test_1");
        zkCustor.createNode("/user/user_1");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNode() {
        log.info(zkCustor.getNodeData("/test"));
    }

    @Test
    public void setNodeData() {
        String path = "/user/user_1";
        zkCustor.setNodeData(path, "张会");
        log.info(zkCustor.getNodeData(path));
    }

    @Test
    public void deleteNode() {
        String path = "/yuntian";
        zkCustor.deleteNode(path);
        zkCustor.deleteNode("/test");
    }

    @Test
    public void treeCache() throws Exception {
        String path = "/test/test-1";
        String errPath = "/test/test-0";

        zkCustor.addTreeCacheListener(path);
        zkCustor.addTreeCacheListener(errPath);

        zkCustor.createNode(path);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        zkCustor.setNodeData(path,"哈哈");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        zkCustor.deleteNode(path);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        zkCustor.createNode(errPath);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        zkCustor.deleteNode(errPath);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        zkCustor.close();
    }

    @Test
    public void getChildren() throws Exception {
        List<String> childrenList = zkCustor.getClient().getChildren().forPath("/");
        System.out.println(childrenList);
    }

    /**
     * 事务测试
     * @throws Exception
     */
    @Test
    public void curatorTransaction () throws Exception {
        // 开启事务
        CuratorTransaction curatorTransaction = zkCustor.getClient().inTransaction();
        String path1="/transaction3";
        String path2="/transaction4";
        Collection<CuratorTransactionResult> commit =
                // 操作1
                curatorTransaction.create().withMode(CreateMode.PERSISTENT).forPath(path1)
                        .and()
                        // 操作2
                        .create().forPath(path2)
                        .and()
                        // 操作3
                        .setData().forPath(path1, "data".getBytes())
                        .and()
                        // 提交事务
                        .commit();
        Iterator<CuratorTransactionResult> iterator = commit.iterator();
        while (iterator.hasNext()){
            CuratorTransactionResult next = iterator.next();
            System.out.println(next.getForPath());
            System.out.println(next.getResultPath());
            System.out.println(next.getType());
        }
    }

    @Test
    public void createlistNode() throws Exception {
        CuratorFramework client=zkCustor.getClient();
        String listNodePath="/list";
        for (int i = 0; i < 10; i++) {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                    .forPath(listNodePath+"/item"+(i+1),String.valueOf(i+1).getBytes());
        }

        String queueNodePath="/queue";
        for (int i = 0; i < 10; i++) {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(queueNodePath+"/item",String.valueOf(i+1).getBytes());
        }
    }


    @Test
    public void listNode() throws Exception {
        CuratorFramework client=zkCustor.getClient();
        String listNodePath="/list";
        List<String> listNodes=client.getChildren().forPath(listNodePath);
        System.out.println(listNodes);//[item2, item1, item8, item7, item9, item4, item3, item10, item6, item5]

        String queueNodePath="/queue";
        List<String> queueNodes=client.getChildren().forPath(queueNodePath);
        //[item0000000009, item0000000007, item0000000008, item0000000005, item0000000006,
        // item0000000003, item0000000004, item0000000001, item0000000002, item0000000000]
        System.out.println(queueNodes);
    }


    @Test
    public void distributedCount() throws Exception {
        //分布式计数器
        DistributedAtomicInteger counter = new DistributedAtomicInteger(zkCustor.getClient(), "/userVisits", new RetryNTimes(3, 100));
        //初始化
        counter.forceSet(0);
        AtomicValue<Integer> value = counter.increment();
        log.info("原值为" + value.preValue());
        log.info("更改后的值为" + value.postValue());
        log.info("状态" + value.succeeded());
    }
}
