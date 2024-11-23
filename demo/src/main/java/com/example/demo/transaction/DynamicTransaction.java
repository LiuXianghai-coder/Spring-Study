package com.example.demo.transaction;

import com.example.demo.common.DynamicDataSource;
import com.example.demo.config.MybatisConfig;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicTransaction
        extends SpringManagedTransaction {

    private final Map<String, Transaction> txMap = new ConcurrentHashMap<>();

    private final DynamicDataSource dataSource;

    public DynamicTransaction(DynamicDataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        String curDataSource = DataSourceHolder.getCurDataSource();
        if (curDataSource == null) {
            return super.getConnection();
        }
        if (txMap.containsKey(curDataSource)) {
            return txMap.get(curDataSource).getConnection();
        }
        txMap.put(curDataSource, new SpringManagedTransaction(dataSource.getResolvedDataSources().get(curDataSource)));
        return txMap.get(curDataSource).getConnection();
    }

    @Override
    public void commit() throws SQLException {
        for (Map.Entry<String, Transaction> entry : txMap.entrySet()) {
            entry.getValue().commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        for (Map.Entry<String, Transaction> entry : txMap.entrySet()) {
            entry.getValue().rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        for (Map.Entry<String, Transaction> entry : txMap.entrySet()) {
            entry.getValue().close();
        }
        txMap.clear();
    }

    @Override
    public Integer getTimeout() throws SQLException {
        return super.getTimeout();
    }
}
