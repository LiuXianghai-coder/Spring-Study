package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxh
 * @date 2022/6/26-上午8:28
 */
@Aspect
public class StatesAspect {
    private final static Logger log = LoggerFactory.getLogger(StatesAspect.class);

    private final Map<String, long[]> timeMap = new HashMap<>();

    @Pointcut("execution(* com.example.demo.tools.DiffTool.*(..))")
    public void execute(){}

    // 所有公有方法的切面
    @Pointcut("execution(public * com.example.demo.tools.DiffTool.*(..))")
    public void compExe(){}

    @Around("execute()")
    public Object statesTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        MethodSignature method = (MethodSignature) pjp.getSignature();
        String methodName = method.getName();

        long[] time = timeMap.getOrDefault(methodName, new long[2]);
        time[0]++; time[1] += System.currentTimeMillis() - startTime;
        timeMap.put(methodName, time);

        return result;
    }

    @After("compExe()")
    public void staticsTimes(JoinPoint jp) {
        log.info("执行结束之后的时间统计");
        for (String key : timeMap.keySet()) {
            long[] val = timeMap.get(key);
            System.out.println(key + " 总共调用 " + val[0] + " 次， 共耗时 " + val[1] + " ms");
        }
    }
}
