package com.yuntian.redis.log;

/**
 * @Auther: yuntian
 * @Date: 2019/8/18 0018 15:12
 * @Description:
 */

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuntian.redis.common.Result;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;


/**
 * 日志切面类
 *
 * @author Administrator
 */
@Aspect
@Component
@Slf4j
public class LogAspect {


    /**
     * ..表示包及子包 该方法代表controller层的所有方法
     */
    @Pointcut("execution(public * com.yuntian.redis.controller..*.*(..))")
    public void controllerMethod() {
    }


    /**
     * 请求
     *
     * @param joinPoint
     * @throws Exception
     */
    @Before("controllerMethod()")
    public void logRequestInfo(JoinPoint joinPoint) throws Exception {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        StringBuilder requestLog = new StringBuilder();
        requestLog.append("请求信息：")
                .append("URL = {")
                .append(request.getRequestURI())
                .append("},")
                .append("HTTP_METHOD = {")
                .append(request.getMethod())
                .append("},").append("IP = {")
                .append(request.getRemoteAddr())
                .append("},").append("CLASS_METHOD = {")
                .append(joinPoint.getSignature().getDeclaringTypeName()).append(".").append(joinPoint.getSignature().getName()).append("}");

        if (joinPoint.getArgs().length == 0) {
            requestLog.append(",ARGS = {} ");
        } else {
            requestLog.append(",ARGS = ").append(new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .writeValueAsString(joinPoint.getArgs()[0]));
        }
        log.info(requestLog.toString());
    }


    /**
     * 返回结果
     *
     * @param result
     * @throws Exception
     */
    @AfterReturning(returning = "result", pointcut = "controllerMethod()")
    public void logResultInfo(Result result) {
        log.info("请求结果：" + JSON.toJSONString(result));
    }

}