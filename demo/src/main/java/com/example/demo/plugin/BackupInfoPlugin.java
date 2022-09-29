package com.example.demo.plugin;

import com.example.demo.common.BackupInfo;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

import static org.apache.ibatis.mapping.SqlCommandType.INSERT;
import static org.apache.ibatis.mapping.SqlCommandType.UPDATE;

/**
 * @author lxh
 */
@Intercepts(value = @Signature(type = StatementHandler.class, method = "prepare",
        args = {Connection.class, Integer.class}))
public class BackupInfoPlugin implements Interceptor {

    private final static Logger log = LoggerFactory.getLogger(BackupInfoPlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (!(target instanceof RoutingStatementHandler)) return invocation.proceed();
        RoutingStatementHandler rh = (RoutingStatementHandler) target;
        Class<?> rhClazz = rh.getClass();
        Field df = rhClazz.getDeclaredField("delegate");
        df.setAccessible(true);

        Object delegate = df.get(target);
        Class<?> delClazz = delegate.getClass();
        if (!BaseStatementHandler.class.isAssignableFrom(delClazz)) return invocation.proceed();

        try {
            MappedStatement ms = (MappedStatement) get("mappedStatement", delegate);
            if (ms == null || isNoBackup(ms.getSqlCommandType())) return invocation.proceed();

            // 原有 BaseStatementHandler 的必需参数
            Configuration config = (Configuration) get("configuration", delegate);
            ObjectFactory factory = (ObjectFactory) get("objectFactory", delegate);
            RowBounds rb = (RowBounds) get("rowBounds", delegate);
            Executor executor = (Executor) get("executor", delegate);
            ResultSetHandler rsh = (ResultSetHandler) get("resultSetHandler", delegate);
            ParameterHandler ph = (ParameterHandler) get("parameterHandler", delegate);
            BoundSql bs = (BoundSql) get("boundSql", delegate);

            // BaseStatementHandler 必需的构造函数参数列表
            Class<?>[] paramTypes = new Class[]{Executor.class, MappedStatement.class,
                    Object.class, RowBounds.class, ResultHandler.class, BoundSql.class};
            Constructor<?> constructor = delegate.getClass().getDeclaredConstructor(paramTypes);

            Object paramObj = parametrizeBackupInfo(bs.getParameterObject());
            Object[] params = new Object[paramTypes.length];
            params[0] = executor;
            params[1] = ms;
            params[2] = paramObj;
            params[3] = rb;
            params[4] = findByList(rsh);
            params[5] = ms.getBoundSql(paramObj);
            df.set(rh, constructor.newInstance(params));
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            log.debug(e.getLocalizedMessage());
            return invocation.proceed();
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    protected Object get(String name, Object target) throws NoSuchFieldException, IllegalAccessException {
        Field field = BaseStatementHandler.class.getDeclaredField(name);
        field.setAccessible(true);
        return field.get(target);
    }

    protected Object parametrizeBackupInfo(Object obj) {
        if (!(obj instanceof BackupInfo)) return obj;
        BackupInfo info = (BackupInfo) obj;
        if (info.getBackUpId() == null) info.setBackUpId(info.getRecordId());
        return info;
    }

    protected ResultHandler<?> findByList(ResultSetHandler rsh) throws IllegalAccessException {
        if (rsh == null) return null;
        Field[] fields = rsh.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (ResultHandler.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                return (ResultHandler<?>) field.get(rsh);
            }
        }
        return null;
    }

    protected static boolean isNoBackup(SqlCommandType type) {
        return type != INSERT && type != UPDATE;
    }
}
