package com.example.demo.plugin;

import com.example.demo.common.BackupInfo;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Method;

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
            if (bakId == null) info.setBackupId(info.getId());
        }
        return method.invoke(target, args);
    }
}
