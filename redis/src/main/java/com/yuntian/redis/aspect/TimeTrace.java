package com.yuntian.redis.aspect;

/**
 * @Auther: yuntian
 * @Date: 2019/8/18 0018 15:37
 * @Description: 自动打印方法的耗时
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface TimeTrace {
}
