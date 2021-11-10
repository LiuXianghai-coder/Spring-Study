package org.xhliu.aop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author xhliu2
 * @create 2021-11-10 16:52
 **/
public class LogResultAdvice implements AfterReturningAdvice {
    private final static Logger log = LoggerFactory.getLogger(LogArgsAdvice.class);

    public void afterReturning(Object o, Method method, Object[] objects, Object o1)
            throws Throwable {
        log.info("方法执行完成之后, 得到的结果: " + o);
    }
}
