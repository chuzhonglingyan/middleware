package com.yuntian.rocketmq.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/10/23 0023 23:32
 * @Description:
 */
@Data
@AllArgsConstructor
public class OrderPaidEvent implements Serializable {

    private String orderId;

    private Integer paidMoney;

}
