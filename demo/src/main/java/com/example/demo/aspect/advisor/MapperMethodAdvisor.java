package com.example.demo.aspect.advisor;

import com.example.demo.aspect.interceptor.MapperMethodInterceptor;
import com.example.demo.aspect.pointcut.MapperMethodPointcut;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

public class MapperMethodAdvisor
        extends AbstractPointcutAdvisor {

    private final Pointcut pointcut = new MapperMethodPointcut();

    private final MethodInterceptor interceptor = new MapperMethodInterceptor();

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return interceptor;
    }
}
