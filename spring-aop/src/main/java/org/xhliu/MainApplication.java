package org.xhliu;

import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhliu.service.MineSubject;
import org.xhliu.service.impl.RealMine;
import org.xhliu.service.impl.RealMineSubject;
import org.xhliu.service.proxy.CglibProxy;
import org.xhliu.service.proxy.JdkDynamicProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class MainApplication {
    private final static Logger log = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        RealMineSubject realMineSubject = new RealMineSubject();
        JdkDynamicProxy proxy = new JdkDynamicProxy(realMineSubject);
        ClassLoader loader = realMineSubject.getClass().getClassLoader();
        MineSubject subject = (MineSubject) Proxy.newProxyInstance(loader, new Class[]{MineSubject.class}, proxy);

        subject.getMessage();

        Enhancer enhancer = new Enhancer();
        Field[] fields = java.lang.ClassLoader.class.getDeclaredFields();
        for (Field field : fields)
            field.setAccessible(true);

        enhancer.setSuperclass(RealMine.class);
        enhancer.setCallback(new CglibProxy());

        RealMine realMine = (RealMine) enhancer.create();
        realMine.sayMessage();
    }
}
