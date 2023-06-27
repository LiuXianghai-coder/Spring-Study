package org.xhliu.springdata.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class CommonAspect {

    private final static Logger log = LoggerFactory.getLogger(CommonAspect.class);

    @Pointcut("execution(public * org.xhliu.springdata.controller.*.*(..))")
    public void pointCut(){
    }

    @Around("pointCut()")
    public Object handler(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Before Handler");
        Object res = pjp.proceed();
        log.info("After Handler");
        return res;
    }
}
