package org.xhliu.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xhliu2
 * @create 2021-11-11 17:04
 **/
@Aspect
public class AdviceExample {
    private final static Logger log = LoggerFactory.getLogger(AdviceExample.class);

    /**
     * 在执行 dataAccessOperation() 方法之前执行以下的方法
     */
    @Before("org.xhliu.aop.aspect.SystemArchitecture.dataAccessOperation()")
    public void daoCheck() {
        log.info("AdviceExample's method daoCheck() invoke.........");
    }

    /**
     * 在执行 org.xhliu.dao.* 的方法之前，执行以下的方法
     */
    @Before("execution(* org.xhliu.dao.*)")
    public void daoExecutionCheck(){
        log.info("AdviceExample's method daoExecutionCheck invoke......");
    }

    /**
     * 在返回语句执行之后执行 dataAccessOperation()
     */
    @AfterReturning("org.xhliu.aop.aspect.SystemArchitecture.dataAccessOperation()")
    public int afterReturningCheck() {
        log.info("AdviceExample's method afterReturningCheck invoke......");
        return Integer.MAX_VALUE;
    }

    /**
     * 在返回语句执行之前执行 dataAccessOperation() 方法
     * @param retVal :
     */
    @AfterReturning(
            pointcut = "org.xhliu.aop.aspect.SystemArchitecture.dataAccessOperation()",
            returning = "retVal"
    )
    public void afterReturningByVal(Object retVal) {
        log.info("AdviceExample's method afterReturningByVal invoke, retVal=" + retVal.toString());
    }

    /**
     * 异常返回
     */
    @AfterThrowing("org.xhliu.aop.aspect.SystemArchitecture.dataAccessOperation()")
    public void afterRecoveryActions() {
        log.info("AdviceExample's method afterRecoveryActions invoke......");
        throw new IllegalArgumentException("Just a Exception....");
    }

    /**
     * 异常返回
     * @param e :
     */
    @AfterThrowing(
            pointcut = "org.xhliu.aop.aspect.SystemArchitecture.dataAccessOperation()",
            throwing = "e"
    )
    public void doRecoveryActions(Exception e) {
        log.info("AdviceExample's method doRecoveryActions invoke......");
        e.printStackTrace();
    }

    /**
     * 这里会拦截正常返回和异常的情况
     */
    @After("org.xhliu.aop.aspect.SystemArchitecture.dataAccessOperation()")
    public void doReleaseLock() {
        log.info("AdviceExample's method doReleaseLock invoke......");
    }

    /**
     * 整合 @Before 和 @AfterReturning
     * @param point
     * @return
     */
    @Around("org.xhliu.aop.aspect.SystemArchitecture.businessService()")
    public Object doBasicProfiling(ProceedingJoinPoint point) throws Throwable {
        log.info("AdviceExample's method doBasicProfiling invoke......");
        return point.proceed();
    }
}
