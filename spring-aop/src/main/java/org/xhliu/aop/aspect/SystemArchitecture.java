package org.xhliu.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定义一些 PointCut，相当于一个程序执行点，
 * 一般来讲，一个方法的执行前、执行后；return 语句执行前、执行后；异常抛出前，抛出后 都会被认为是一个 PointCut
 *
 * 具体要在这些执行点进行何种操作，需要配置相应的织入类来定义
 *
 * @author xhliu2
 * @create 2021-11-11 16:55
 **/
@Aspect
public class SystemArchitecture {
    private final static Logger log = LoggerFactory.getLogger(SystemArchitecture.class);

    /*
        使用 @PointCut 注解来定义切点

        匹配切点的方式主要有以下几种（都支持正则表达式匹配）
            execution：用于匹配方法签名
            within：表示当前所在类或者所在包下面的方法
            @annotation：方法上存在的特定注解，如 @PointCut("execution(.*(..)) && @annotation(Subscribe)")
                         就会匹配所有方法参数中带有 @Subscribe 注解的方法
            bean：用于匹配 Bean 的名字
    */

    /**
     * org.xhliu.web 包下的所有类中存在的方法都定义一个 PointCut
     */
    @Pointcut("within(org.xhliu.web.*)")
    public void inWebLayer(){
    }

    /**
     * 为 org.xhliu.service 包下的所有类中存在的方法都定义一个 PointCut
     */
    @Pointcut("within(org.xhliu.service.impl.*)")
    public void inServiceLayer(){
    }

    /**
     * 为 org.xhliu.dao 包下的所有类中存在的方法创建 PointCut
     */
    @Pointcut("within(org.xhliu.dao.*)")
    public void inDaoLayer(){
    }

    /**
     * 使用 '.' 来表示一个包名，'..' 表示当前包以及子包，(..) 则表示匹配方法的任意参数
     *
     * 为 org.xhliu.service 类中存在的方法创建 PointCut
     */
    @Pointcut("within(org.xhliu.aop.service.impl.*)")
    public void businessService(){
    }

    /**
     * 为 org.xhliu.dao 包及子包中的所有类声明的方法创建 PointCut
     */
    @Pointcut("execution(* org.xhliu.dao.*.*(..))")
    public void dataAccessOperation(){
    }
}
