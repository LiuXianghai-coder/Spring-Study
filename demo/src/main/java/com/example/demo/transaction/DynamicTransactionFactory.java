package com.example.demo.transaction;

import com.example.demo.config.MybatisConfig;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;

public class DynamicTransactionFactory
        extends SpringManagedTransactionFactory {

    @Override
    public Transaction newTransaction(DataSource dataSource,
                                      TransactionIsolationLevel level,
                                      boolean autoCommit) {
        return new DynamicTransaction((MybatisConfig.DynamicDataSource) dataSource);
    }
}
