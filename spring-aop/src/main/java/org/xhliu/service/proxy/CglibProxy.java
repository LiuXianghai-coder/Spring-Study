package org.xhliu.service.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
    /**
     * @param o : 对应 UML 图中的 RealSubject 对象
     * @param method ： 代理的方法
     * @param objects ： 传入代理代理方法的参数
     * @param methodProxy ： 实际代理对象
     */
    public Object intercept(
            Object o,
            Method method,
            Object[] objects,
            MethodProxy methodProxy
    ) throws Throwable {
        before();
        Object message = methodProxy.invokeSuper(o, objects);
        System.out.println("Get Message: " + message.toString());
        after();
        return null;
    }

    private void before() {
        System.out.println("Before Method invoke.....");
    }

    private void after() {
        System.out.println("After Method invoke.....");
    }
}
