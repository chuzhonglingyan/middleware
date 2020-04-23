package com.yuntian.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuntian
 * @date 2020/4/3 0003 16:03
 * @description
 */
@Data
@AllArgsConstructor
public class Article {

    private Long id;

    private Long likeCount;


}
