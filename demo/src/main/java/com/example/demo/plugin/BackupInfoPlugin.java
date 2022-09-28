package com.example.demo.plugin;

import com.example.demo.common.BackupInfo;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author lxh
 */
@Intercepts(value = @Signature(type = Executor.class, method = "update",
        args = {MappedStatement.class, Object.class}))
public class BackupInfoPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();
        if (args.length < 2) return invocation.proceed();
        Object obj = args[1];
        Object target = invocation.getTarget();
        if (obj instanceof BackupInfo) {
            BackupInfo info = (BackupInfo) obj;
            String bakId = info.getBackupId();
            if (bakId == null) info.setBackupId(info.getRecordId());
        }
        return method.invoke(target, args);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
