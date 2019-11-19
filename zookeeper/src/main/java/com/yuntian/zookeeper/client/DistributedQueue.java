package com.yuntian.zookeeper.client;

import com.alibaba.fastjson.JSON;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/8/18 0018 21:56
 * @Description: 分布式队列
 */
public class DistributedQueue<E> implements Queue<E> {


    private CuratorFramework client;

    private final static String ROOT_PATH = "/queue";

    private String queueNodePath;

    private final static String PREFIX = "item";


    public DistributedQueue(ZKCustor zkCustor,String name) {
        this.queueNodePath = ROOT_PATH.concat("_").concat(name);
        client = zkCustor.getClient();
    }

    private String getChildeNodePath() {
        return queueNodePath.concat("/").concat(PREFIX);
    }

    @Override
    public boolean offer(E o) {
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(getChildeNodePath(), JSON.toJSONBytes(o));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public E peek() {
        return getFront();
    }

    @Override
    public E poll() {
        return pollFront();
    }


    public E getFront() {
        String path = getFrontPath(getChildNodeList());
        if (StringUtils.isEmpty(path)){
            return null;
        }
        try {
            byte[] data = client.getData().forPath(queueNodePath + "/" + path);
            return JSON.parseObject(data, GenericsUtils.getSuperClassGenricType(getClass()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private E pollFront() {
        String path = getFrontPath(getChildNodeList());
        if (StringUtils.isEmpty(path)){
            return null;
        }
        try {
            byte[] data = client.getData().forPath(queueNodePath + "/" + path);
            client.delete().forPath(path);
            return JSON.parseObject(data, GenericsUtils.getSuperClassGenricType(getClass()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getFrontPath(List<String> pathList) {
        if (CollectionUtils.isEmpty(pathList)){
            return "";
        }
        return pathList.stream().min(Comparator.comparing(Function.identity())).get();
    }

    public String getTailPath(List<String> pathList) {
        if (CollectionUtils.isEmpty(pathList)){
            return "";
        }
        return pathList.stream().max(String::compareTo).get();
    }


    public List<String> getChildNodeList() {
        try {
            List<String> listChildNode = client.getChildren().forPath(queueNodePath);
            if (listChildNode.size() == 0) {
                return new ArrayList<>();
            }
            return listChildNode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public E get(Integer index) {
        return null;
    }

    @Override
    public Integer index(E o) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        try {
            return client.getChildren().forPath(queueNodePath).size() == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int size() {
        try {
            return client.getChildren().forPath(queueNodePath).size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
