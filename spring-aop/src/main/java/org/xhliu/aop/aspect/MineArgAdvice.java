package org.xhliu.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class MineArgAdvice {
    private final static Logger log = LoggerFactory.getLogger(MineArgAdvice.class);

    public void logResult(Object result) {
        log.info("Get Result = " + result.toString());
    }

    public void logArgs(JoinPoint joinPoint) {
        log.info("Before method execution, print params = " + Arrays.toString(joinPoint.getArgs()));
    }
}
