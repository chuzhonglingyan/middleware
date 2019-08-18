package com.yuntian.redis.common;

import lombok.Data;

/**
 * @Auther: yuntian
 * @Date: 2019/8/18 0018 13:45
 * @Description:
 */
@Data
public class Result<T> {


    private T data;


    private int code;

    private String msg;



}
