package com.example.demo.plugin;

import com.example.demo.common.BackupInfo;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Method;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author lxh
 */
@Intercepts(value = @Signature(type = StatementHandler.class, method = "update",
        args = {Statement.class}))
public class BackupInfoPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Method method = target.getClass().getDeclaredMethod("getBoundSql");
        Object param = method.invoke(target);
        if (!(param instanceof BoundSql)) return invocation.proceed();
        BoundSql bs = (BoundSql) param;
        Object obj = bs.getParameterObject();
        if (!(obj instanceof BackupInfo)) return invocation.proceed();
        BackupInfo info = (BackupInfo) obj;
        if (info.getBackupId() == null) info.setBackupId(info.getRecordId());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
