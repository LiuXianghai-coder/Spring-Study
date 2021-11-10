package org.xhliu.aop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author xhliu2
 * @create 2021-11-10 16:50
 **/
public class LogArgsAdvice implements MethodBeforeAdvice {
    private final static Logger log = LoggerFactory.getLogger(LogArgsAdvice.class);

    public void before(Method method, Object[] objects, Object o) {
        log.info("准备执行方法: " + method.getName() + ", 参数列表: " + Arrays.toString(objects));
    }
}
