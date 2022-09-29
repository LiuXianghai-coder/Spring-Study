package com.example.demo.plugin;

import com.example.demo.common.BackupInfo;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author lxh
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class BackUpInfoReadPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (null == result) return null;
        if (!(result instanceof List) || ((List<?>) result).isEmpty()) return result;
        List<?> list = (List<?>) result;
        for (Object obj : list) {
            if (!(obj instanceof BackupInfo)) continue;
            BackupInfo info = (BackupInfo) obj;
            if (info.getBackUpId() != null) continue;
            info.setBackUpId(info.getRecordId());
        }
        return list;
    }
}
