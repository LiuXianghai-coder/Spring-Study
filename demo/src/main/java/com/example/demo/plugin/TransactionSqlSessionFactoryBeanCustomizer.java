package com.example.demo.plugin;

import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SqlSessionFactoryBeanCustomizer;

public class TransactionSqlSessionFactoryBeanCustomizer
        implements SqlSessionFactoryBeanCustomizer {

    private final TransactionFactory txFactory;

    public TransactionSqlSessionFactoryBeanCustomizer(TransactionFactory txFactory) {
        this.txFactory = txFactory;
    }

    @Override
    public void customize(SqlSessionFactoryBean factoryBean) {
        factoryBean.setTransactionFactory(txFactory);
    }
}
