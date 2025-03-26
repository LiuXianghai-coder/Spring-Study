package com.example.demo.plugin;

import cn.hutool.core.lang.Snowflake;
import com.example.demo.entity.OaStatistic;
import com.example.demo.tools.IdTool;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 *@author lxh
 */
@Intercepts({@Signature(type = Executor.class, method = "update",
        args = {MappedStatement.class, Object.class})})
public class OaStatisticSplitPlugin
        implements Interceptor {

    private final static Logger log = LoggerFactory.getLogger(OaStatisticSplitPlugin.class);

    static final LocalDateTime DEFAULT_TIME = LocalDateTime.of(LocalDate.of(2025, 1, 1), LocalTime.MIDNIGHT);

    static final Map<LocalDateTime, String> TIME_TO_TABLE = new TreeMap<>();

    static final Map<LocalDateTime, Integer> ID_CENTER_MAP = new HashMap<>();

    static {
        LocalDateTime firstQuarter = LocalDateTime.of(LocalDate.of(2025, 1, 1), LocalTime.MIDNIGHT);
        LocalDateTime secondQuarter = LocalDateTime.of(LocalDate.of(2025, 4, 1), LocalTime.MIDNIGHT);
        LocalDateTime thirdQuarter = LocalDateTime.of(LocalDate.of(2025, 7, 1), LocalTime.MIDNIGHT);

        TIME_TO_TABLE.put(firstQuarter, "oa_statistic_2025_a");
        TIME_TO_TABLE.put(secondQuarter, "oa_statistic_2025_b");
        TIME_TO_TABLE.put(thirdQuarter, "oa_statistic_2025_c");

        ID_CENTER_MAP.put(firstQuarter, 1);
        ID_CENTER_MAP.put(secondQuarter, 2);
        ID_CENTER_MAP.put(thirdQuarter, 3);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();

        OaStatistic param = findParam(args);
        if (Objects.isNull(param)) {
            return invocation.proceed();
        }
        LocalDateTime quarter = determineQuarter(param.getCreatedTime());
        String tableName = TIME_TO_TABLE.get(quarter);
        param.setTableName(tableName);
        if (Objects.isNull(param.getId())) {
            param.setId(IdTool.snowFlake(ID_CENTER_MAP.get(quarter)));
        }
        return invocation.proceed();
    }

    private LocalDateTime determineQuarter(LocalDateTime createdTime) {
        if (Objects.isNull(createdTime)) {
            return DEFAULT_TIME;
        }
        LocalDateTime prev = DEFAULT_TIME;
        for (LocalDateTime localDateTime : TIME_TO_TABLE.keySet()) {
            if (!createdTime.isBefore(localDateTime)) {
                prev = localDateTime;
                continue;
            }
            return prev;
        }
        return prev;
    }

    private OaStatistic findParam(Object[] args) {
        for (Object arg : args) {
            if (!(arg instanceof OaStatistic)) continue;
            return (OaStatistic) arg;
        }
        return null;
    }
}
