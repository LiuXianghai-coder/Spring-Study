package com.example.demo.aspect.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExceptionHandlerInterceptor
        implements MethodInterceptor {

    private final static Logger log = LoggerFactory.getLogger(ExceptionHandlerInterceptor.class);

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        try {
            log.info("准备执行 Mapper 方法。。。。。。。");
            return invocation.proceed();
        } catch (Throwable t) {
            log.error("", t);
            throw t;
        }
    }
}
