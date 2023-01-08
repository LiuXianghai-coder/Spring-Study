package com.example.demo;

import com.example.demo.mapper.UserInfoMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.mapper.common.Mapper;

import java.lang.reflect.*;
import java.util.Objects;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@tk.mybatis.spring.annotation.MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {

    static Class<?> mapperEntityClass(Mapper<?> mapper) {
        InvocationHandler handler = Proxy.getInvocationHandler(mapper);
        Field mapperInterface = handler.getClass().getDeclaredFields()[5];
        mapperInterface.setAccessible(true);
        try {
            Class<?> clazz = (Class<?>) mapperInterface.get(handler);
            Object genericInfo = invokeMethod("getGenericInfo", clazz);
            if (genericInfo == null) return null;
            Type st = ((Type[]) (Objects.requireNonNull(invokeMethod("getSuperInterfaces", genericInfo))))[0];
            return (Class<?>) ((ParameterizedType) st).getActualTypeArguments()[0];
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static Object invokeMethod(String methodName, Object target, Object... args) {
        Method[] methods = target.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                try {
                    method.setAccessible(true);
                    Object res = method.invoke(target, args);
                    method.setAccessible(false);
                    return res;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        SqlSessionFactory factory = context.getBean(SqlSessionFactory.class);
        UserInfoMapper mapper = context.getBean(UserInfoMapper.class);
        System.out.println(mapperEntityClass(mapper));
    }
}
