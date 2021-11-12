package org.xhliu.aop.entity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xhliu2
 * @create 2021-11-12 9:31
 **/
@Aspect
public class ProfilingAspect {
    private final static Logger log = LoggerFactory.getLogger(ProfilingAspect.class);

    @Pointcut("execution(* org.xhliu.aop.entity.Account.*(..))")
    public void modelLayer() {}

    @Around("modelLayer()")
    public Object logProfile(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("[ProfilingAspect] 方法：【" + joinPoint.getSignature()
                + "】结束，耗时：" + (System.currentTimeMillis() - startTime));

        return result;
    }
}
