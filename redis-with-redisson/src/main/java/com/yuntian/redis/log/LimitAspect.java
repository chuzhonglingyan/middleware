package com.yuntian.redis.log;

import com.yuntian.redis.aspect.LimitVerification;
import com.yuntian.redis.service.RedisLuaService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/8/18 0018 15:29
 * @Description:
 */
@Aspect
@Component
@Slf4j
public class LimitAspect {

    @Resource
    private RedisLuaService redisLuaService;

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.yuntian.redis.aspect.LimitVerification * *(..))")
    public void methodAnnotatedWithTrace() {
    }

    /**
     * 构造方法切入点
     */
    @Pointcut("execution(@com.yuntian.redis.aspect.LimitVerification *.new(..))")
    public void constructorAnnotatedTrace() {
    }


    /**
     * @param joinPoint
     * @return
     * @throws Throwable
     * @Around:注入到class文件中的代码
     */
    @Around("methodAnnotatedWithTrace() || constructorAnnotatedTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //获得方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //连接点: 一个方法调用或者方法入口。
        LimitVerification limitVerification = methodSignature.getMethod().getAnnotation(LimitVerification.class);
        boolean verifPass = true;
        if (limitVerification != null) {
            String key = limitVerification.key();
            Integer times = limitVerification.times();
            Integer expire = limitVerification.expire();
            verifPass = redisLuaService.rateLimit(key, times, expire);
        }
        if (verifPass) {
            return joinPoint.proceed();
        }
        return null;
    }


}
