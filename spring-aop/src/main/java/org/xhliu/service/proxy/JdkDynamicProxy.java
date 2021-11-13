package org.xhliu.service.proxy;

import org.xhliu.service.MineSubject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkDynamicProxy implements InvocationHandler {
    private final MineSubject mineSubject;

    public JdkDynamicProxy(MineSubject mineSubject) {
        this.mineSubject = mineSubject;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        System.out.println(proxy.getClass().getName());
        Object result = method.invoke(mineSubject, args);
        System.out.println("Get Result: " + result.toString());
        after();
        return result;
    }

    private void before() {
        System.out.println("Before Method invoke.....");
    }

    private void after() {
        System.out.println("After Method invoke.....");
    }
}
