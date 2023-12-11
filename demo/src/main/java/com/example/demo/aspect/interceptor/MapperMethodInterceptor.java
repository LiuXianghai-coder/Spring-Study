package com.example.demo.aspect.interceptor;

import com.google.common.base.Stopwatch;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class MapperMethodInterceptor
        implements MethodInterceptor {

    private final static Logger log = LoggerFactory.getLogger(MapperMethodInterceptor.class);

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Method method = invocation.getMethod();
        log.info("======== 准备执行 {} ======", method.getName());
        Object result = invocation.proceed();
        log.info("======= {} 耗时 {} ms ======", method.getName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return result;
    }
}
