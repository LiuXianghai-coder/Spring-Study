package org.xhliu.springtransaction.transaction;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;

/**
 * 重新定义 MyBatis 中的事务工厂，使得自定义的动态数据源事务能够被 MyBatis 加载
 *
 * @author lxh
 */
public class DynamicTransactionFactory
        extends SpringManagedTransactionFactory {

    @Override
    public Transaction newTransaction(DataSource dataSource,
                                      TransactionIsolationLevel level,
                                      boolean autoCommit) {
        return DynamicDataSourceUtils.getDynamicTransaction(this, dataSource);
    }
}
