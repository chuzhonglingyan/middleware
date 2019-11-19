package com.yuntian.zookeeper.client;

/**
 * @Auther: yuntian
 * @Date: 2019/8/18 0018 21:57
 * @Description:
 */
public interface Queue<E> {


    boolean offer(E e) throws Exception;

    E peek();


    E poll();

    E get(Integer index);


    Integer index(E  e);


    boolean isEmpty();


    int size();

}
