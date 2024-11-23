package com.example.demo.common;

import com.example.demo.transaction.DataSourceHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Objects;

/**
 * 动态数据源的配置类型
 *
 *@author lxh
 */
public class DynamicDataSource
        extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String curDataSource = DataSourceHolder.getCurDataSource();
        return Objects.requireNonNullElse(curDataSource, defaultDbType());
    }

    protected String defaultDbType() {
        return "mysql";
    }
}
