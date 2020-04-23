package com.yuntian.redis.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuntian
 * @date 2020/4/2 0002 18:11
 * @description
 */
@Data
@AllArgsConstructor
public class UserInfo {
    public UserInfo() {
    }

    private Long id;
    private String name;

    private BigDecimal score;


}
