package com.example.demo.app;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xhliu
 * @create 2022-05-27-15:40
 **/
@Aspect
@Component
public class FormatAspect {
    private final static Logger log = LoggerFactory.getLogger(FormatAspect.class);

    @Around("@annotation(com.example.demo.app.FormatOp)")
    public Object handler(ProceedingJoinPoint pjp) throws Throwable {
        log.info("切面开始工作");
        return pjp.proceed();
    }

    @Pointcut("execution(* com.example.demo.*.* (..))")
    public void point(){}
}
