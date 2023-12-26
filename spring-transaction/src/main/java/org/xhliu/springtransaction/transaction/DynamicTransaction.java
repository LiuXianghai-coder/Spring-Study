package org.xhliu.springtransaction.transaction;

import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.jetbrains.annotations.NotNull;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.xhliu.springtransaction.datasource.DataSourceHolder;
import org.xhliu.springtransaction.datasource.DataSourceType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lxh
 */
public class DynamicTransaction
        extends SpringManagedTransaction {

    private final Map<DataSourceType, Transaction> txMap = new ConcurrentHashMap<>();

    private final DataSource dataSource;

    // 这个属性的目的是为了提供事务上下文中，访问当前事务对象的 key
    private final TransactionFactory txFactory;

    public DynamicTransaction(DataSource dataSource,
                              TransactionFactory txFactory) {
        super(dataSource);
        this.dataSource = dataSource;
        this.txFactory = txFactory;
    }

    public TransactionFactory getTxFactory() {
        return txFactory;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = getConnection(determineDataSourceType());
        connection.setAutoCommit(false);
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
        for (Map.Entry<DataSourceType, Transaction> entry : txMap.entrySet()) {
            entry.getValue().getConnection().rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        for (Map.Entry<DataSourceType, Transaction> entry : txMap.entrySet()) {
            DataSourceUtils.releaseConnection(entry.getValue().getConnection(),
                    curDataSource(determineDataSourceType()));
        }
    }

    @NotNull
    private static DataSourceType determineDataSourceType() {
        DataSourceType type = DataSourceHolder.getCurDataSource();
        if (type == null) type = DataSourceType.MYSQL;
        return type;
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
