package com.example.demo.plugin;

import com.example.demo.entity.RateInfo;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author xhliu
 * @create 2022-08-15-9:01
 **/
@Intercepts(@Signature(
        type = Executor.class, method = "update",
        args = {MappedStatement.class, Object.class}))
public class BackupPlugin implements Interceptor {

    private final static Logger log = LoggerFactory.getLogger(BackupPlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();

        RateInfo param = findParam(args);
        if (param == null) return invocation.proceed();
        if (param.getBackUpId() == null) {
            param.setBackUpId(String.valueOf(param.getId()));
        }
        return method.invoke(target, args);
    }

    private RateInfo findParam(Object[] args) {
        for (Object arg : args) {
            if (!(arg instanceof RateInfo)) continue;
            return (RateInfo) arg;
        }
        return null;
    }
}
