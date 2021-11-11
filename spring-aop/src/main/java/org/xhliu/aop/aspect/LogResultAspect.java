package org.xhliu.aop.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xhliu2
 * @create 2021-11-11 17:33
 **/
@Aspect
public class LogResultAspect {
    private final static Logger log = LoggerFactory.getLogger(LogArgsAspect.class);

    /**
     * 织入对应的 PointCut，同时执行自定义的一些操作
     * @param result ： 由于这里匹配的 PointCut 在 return 语句执行之后，因此可以得到对应方法执行后的返回结果
     */
    @AfterReturning(
            pointcut = "org.xhliu.aop.aspect.SystemArchitecture.businessService()",
            returning = "result"
    )
    public void logResult(Object result) {
        log.info("Get Result = " + result.toString());
    }
}
