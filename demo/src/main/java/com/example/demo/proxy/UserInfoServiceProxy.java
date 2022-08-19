package com.example.demo.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author lxh
 */
public class UserInfoServiceProxy implements MethodInterceptor {

    private final static Logger log = LoggerFactory.getLogger(UserInfoServiceProxy.class);

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("Method Name {} ", method.getName());
        return methodProxy.invoke(o, objects);
    }
}
