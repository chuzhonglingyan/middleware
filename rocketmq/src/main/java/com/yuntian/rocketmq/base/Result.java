package com.yuntian.rocketmq.base;

import lombok.Data;

/**
 * @author guangleilei.
 * @date Created in 11:30 2019/11/19
 * @description
 */
@Data
public class Result<T> {

    private T data;

    private String msg;


    private Integer code;

}
