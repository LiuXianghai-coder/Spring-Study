package com.example.demo.aspect.pointcut;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import tk.mybatis.mapper.common.Mapper;

public class MapperMethodPointcut
        implements Pointcut {

    @Override
    public ClassFilter getClassFilter() {
        return Mapper.class::isAssignableFrom;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return MethodMatcher.TRUE;
    }
}
