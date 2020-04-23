package com.yuntian.redis.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

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
public class TimeAspect {

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.yuntian.redis.aspect.TimeTrace * *(..))")
    public void methodAnnotatedWithTrace() {
    }

    /**
     * 构造方法切入点
     */
    @Pointcut("execution(@com.yuntian.redis.aspect.TimeTrace *.new(..))")
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
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        //方法执行之前
        long starTime = System.currentTimeMillis();
        //连接点: 一个方法调用或者方法入口。
        Object result = joinPoint.proceed();
        //执行完成
        log.info(buildLogMessage(className, methodName, System.currentTimeMillis() - starTime));
        return result;
    }


    /**
     * Create a log message.
     *
     * @param methodName     A string with the method name.
     * @param methodDuration Duration of the method in milliseconds.
     * @return A string representing message.
     */
    private static String buildLogMessage(String className, String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append(className);
        message.append(" --> ");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]");
        return message.toString();
    }


}
