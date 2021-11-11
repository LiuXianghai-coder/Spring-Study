package org.xhliu.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xhliu2
 * @create 2021-11-11 16:55
 **/
@Aspect
public class SystemArchitecture {
    private final static Logger log = LoggerFactory.getLogger(SystemArchitecture.class);

    // 对应 web 层
    @Pointcut("within(org.xhliu.web.*)")
    public void inWebLayer(){
    }

    // 对应 service 层
    @Pointcut("within(org.xhliu.service.*)")
    public void inServiceLayer(){
    }

    // 对应 dao 层
    @Pointcut("within(org.xhliu.dao.*)")
    public void inDaoLayer(){
    }

    // 对应 service 的实现
    @Pointcut("within(org.xhliu.service..*)")
    public void businessService(){
    }

    // dao 实现
    @Pointcut("execution(* org.xhliu.dao.*.*(..))")
    public void dataAccessOperation(){
    }
}
