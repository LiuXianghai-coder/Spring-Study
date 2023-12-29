package org.xhliu.springtransaction.transaction;

import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.xhliu.springtransaction.datasource.DataSourceType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于定义在当前 MyBatis 处理上下文中，正在被使用的事务对象类型，由于现有的 {@link SpringManagedTransaction}
 * 实现只能绑定到一个数据源，在基于 {@link AbstractRoutingDataSource} 的数据源中，当同属于一个事务时，无法切换到希望的
 * 数据源，为此，需要定义一个特殊的事务类型来替换现有的事务类型，从而实现在一个事务中能够切换数据源的效果
 *
 * @author lxh
 */
public class DynamicTransaction
        extends SpringManagedTransaction {

    // 当前数据源之间的映射关系
    private final Map<DataSourceType, Transaction> txMap = new ConcurrentHashMap<>();

    // 实际当前系统中持有的数据源对象
    private final DataSource dataSource;

    public DynamicTransaction(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = getConnection(DynamicDataSourceUtils.determineDataSourceType());
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            connection.setAutoCommit(false); // 如果当前已经持有了事务，那么获取到的连接应当都是非自动提交的
        }
        return connection;
    }

    @Override
    public void commit() throws SQLException {
        for (Map.Entry<DataSourceType, Transaction> entry : txMap.entrySet()) {
            if (!entry.getValue().getConnection().getAutoCommit()) {
                entry.getValue().getConnection().commit();
            }
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (TransactionSynchronizationManager.isActualTransactionActive()) return;
        for (Map.Entry<DataSourceType, Transaction> entry : txMap.entrySet()) {
            entry.getValue().getConnection().rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        if (TransactionSynchronizationManager.isActualTransactionActive()) return;
        for (Map.Entry<DataSourceType, Transaction> entry : txMap.entrySet()) {
            DataSourceUtils.releaseConnection(entry.getValue().getConnection(), curDataSource(entry.getKey()));
        }
    }

    private Connection getConnection(DataSourceType type) throws SQLException {
        if (txMap.containsKey(type)) {
            return txMap.get(type).getConnection();
        }

        DataSource curDS = dataSource;
        while (curDS instanceof DelegatingDataSource) {
            curDS = ((DelegatingDataSource) curDS).getTargetDataSource();
        }
        if (curDS instanceof AbstractRoutingDataSource) {
            Map<Object, DataSource> dss = ((AbstractRoutingDataSource) curDS).getResolvedDataSources();
            DataSource actualDS = dss.getOrDefault(type, ((AbstractRoutingDataSource) curDS).getResolvedDefaultDataSource());
            txMap.put(type, new SpringManagedTransaction(actualDS));
            return txMap.get(type).getConnection();
        }

        txMap.put(type, new SpringManagedTransaction(curDS));
        return txMap.get(type).getConnection();
    }

    private DataSource curDataSource(DataSourceType type) {
        DataSource curDS = dataSource;
        while (curDS instanceof DelegatingDataSource) {
            curDS = ((DelegatingDataSource) curDS).getTargetDataSource();
        }
        if (curDS instanceof AbstractRoutingDataSource) {
            Map<Object, DataSource> dss = ((AbstractRoutingDataSource) curDS).getResolvedDataSources();
            return dss.getOrDefault(type, ((AbstractRoutingDataSource) curDS).getResolvedDefaultDataSource());
        }
        return curDS;
    }
}
