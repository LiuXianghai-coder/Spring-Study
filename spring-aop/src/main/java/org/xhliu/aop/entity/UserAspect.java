package org.xhliu.aop.entity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class UserAspect {
    private final static Logger log = LoggerFactory.getLogger(UserAspect.class);

    @Pointcut("execution(* com.example.aopshare.entity.*.*(..))")
    public void modelLayer() {}

    @Around("modelLayer()")
    public Object logProfile(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("[UserAspect] 方法：【" + joinPoint.getSignature()
                + "】结束，耗时：" + (System.currentTimeMillis() - startTime));

        return result;
    }

}
