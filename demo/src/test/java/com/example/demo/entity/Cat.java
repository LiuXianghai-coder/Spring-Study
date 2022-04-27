package com.example.demo.entity;

/**
 * @author xhliu
 * @create 2022-03-22-17:24
 **/
public class Cat {
    // 注意这里使用了 final 关键字修饰 INSTANCE，由于 final 域的内存语义，Cat 的构造函数初始化会在将对象引用给 INSTANCE 之前全部完成，从而使得得到的 INSTANCE 实例是有效的
    private static final Cat INSTANCE = new Cat();

    private Cat() {
        // 防止客户端使用反射的方式来再次初始化实例
        if (null != INSTANCE)
            try {
                throw new IllegalAccessException("只能初始化一次");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
    }

    // 使用静态工厂方法的方式获取实例，具体可以看看 《Effective Java》给出的第一条建议
    public static Cat getInstance() {
        return INSTANCE;
    }
}

