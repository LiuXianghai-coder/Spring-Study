package org.xhliu.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author xhliu2
 * @create 2021-11-11 17:31
 **/
@Aspect
public class LogArgsAspect {
    private final static Logger log = LoggerFactory.getLogger(LogArgsAspect.class);

    @Before("org.xhliu.aop.aspect.SystemArchitecture.businessService()")
    public void logArgs(JoinPoint joinPoint) {
        log.info("Before method execution, print params = " + Arrays.toString(joinPoint.getArgs()));
    }
}
