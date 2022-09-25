package com.example.demo.plugin;

import com.example.demo.entity.RateInfo;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author lxh
 */
@Intercepts({@Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class})})
public class ParamFieldPlugin implements Interceptor {

    private final static Logger log = LoggerFactory.getLogger(ParamFieldPlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();

        if (args == null || args.length != 2) return null;
        if (!(args[1] instanceof RateInfo)) return null;
        RateInfo obj = (RateInfo) args[1];
        obj.setRateName("LPR");

        return method.invoke(target, args);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
