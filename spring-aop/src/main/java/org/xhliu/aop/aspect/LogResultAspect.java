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

    @AfterReturning(
            pointcut = "org.xhliu.aop.aspect.SystemArchitecture.businessService()",
            returning = "result"
    )
    public void logResult(Object result) {
        log.info(result.toString());
    }
}
