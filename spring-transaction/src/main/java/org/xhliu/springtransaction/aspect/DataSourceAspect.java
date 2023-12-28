package org.xhliu.springtransaction.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xhliu.springtransaction.annotation.DataSource;
import org.xhliu.springtransaction.datasource.DataSourceHolder;
import org.xhliu.springtransaction.datasource.DataSourceType;

@Aspect
@Component
public class DataSourceAspect {

    private final static Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    @Around("@annotation(org.xhliu.springtransaction.annotation.DataSource)")
    public Object dataSourceSelect(ProceedingJoinPoint pjp) throws Throwable {
        DataSourceType prevType = DataSourceHolder.getCurDataSource();
        DataSource dataSource = parseDataSourceAnno(pjp);
        try {
            log.debug("当前执行的上下文中，数据源的所属类型: {}", dataSource.value());
            DataSourceHolder.setCurDataSource(dataSource.value());
            return pjp.proceed();
        } finally {
            DataSourceHolder.setCurDataSource(prevType);
        }
    }

    private static DataSource parseDataSourceAnno(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        DataSource dataSource = signature.getMethod().getDeclaredAnnotation(DataSource.class);
        if (dataSource != null) return dataSource;
        Object target = pjp.getTarget();
        return target.getClass().getDeclaredAnnotation(DataSource.class);
    }
}
