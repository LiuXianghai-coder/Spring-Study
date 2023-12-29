package org.xhliu.springtransaction.transaction;

import org.apache.ibatis.transaction.TransactionFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.xhliu.springtransaction.datasource.DataSourceHolder;
import org.xhliu.springtransaction.datasource.DataSourceType;

import javax.sql.DataSource;

/**
 * 和动态数据源有关的工具类
 *
 * @author lxh
 */
public class DynamicDataSourceUtils {

    private final static Logger log = LoggerFactory.getLogger(DynamicDataSourceUtils.class);

    /**
     * 如果当前处理的上下文处于一个事务，那么会检测在事务中是否存在当前事务工厂的资源对象，如果存在，则直接返回当前
     * 事务持有的对应资源对象，否则，将会创建一个新的事务资源对象，并将其通过 {@link TransactionFactory} 作为 {@code key}
     * 绑定到一个事务中
     * 如果当前处理的上下文并不处于同一个事务，那么将会直接创建一个新的 {@link DynamicTransaction} 对象
     *
     * @param txFactory 当前系统中，默认的事务工厂
     * @param dataSource 当前系统中，实际的数据源对象
     * @return 当前持有的事务对象
     * @see org.mybatis.spring.SqlSessionUtils
     * @see TransactionSynchronizationManager
     */
    public static DynamicTransaction getDynamicTransaction(TransactionFactory txFactory,
                                                           DataSource dataSource) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) { // 如果当前持有事务
            Assert.notNull(txFactory, "查询的动态数据源的同步 key 不能为 null");
            DynamicDataSourceTransactionHolder holder =
                    (DynamicDataSourceTransactionHolder) TransactionSynchronizationManager.getResource(txFactory);
            DynamicTransaction transaction = txHolder(holder);
            if (transaction != null) {
                return transaction;
            }

            log.debug("Create new DynamicTransaction");
            transaction = new DynamicTransaction(dataSource);

            registerTransactionHolder(txFactory, transaction, dataSource);

            return transaction;
        }

        return new DynamicTransaction(dataSource);
    }

    /**
     * 通过当前持有的动态事务资源对象，获取实际的动态事务对象
     *
     * @param holder 当前事务中持有的动态事务资源持有对象
     * @return 当前事务资源持有的动态事务对象
     */
    private static DynamicTransaction txHolder(DynamicDataSourceTransactionHolder holder) {
        DynamicTransaction tx = null;
        if (holder != null && holder.isSynchronizedWithTransaction()) {
            holder.requested();
            log.debug("Fetch DynamicDataSource Transaction {}", holder.getTx());
            tx = holder.getTx();
        }
        return tx;
    }

    private static void registerTransactionHolder(TransactionFactory txFactory,
                                                  DynamicTransaction tx,
                                                  DataSource dataSource) {
        DynamicDataSourceTransactionHolder holder;
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            holder = new DynamicDataSourceTransactionHolder(txFactory, tx, dataSource);

            log.debug("Registering transaction synchronization for TransactionFactory [{}]", txFactory);
            TransactionSynchronizationManager.bindResource(txFactory, holder);
            TransactionSynchronizationManager
                    .registerSynchronization(new DynamicTransactionSynchronization(holder, txFactory));
            holder.setSynchronizedWithTransaction(true);
            holder.requested();
        } else {
            log.debug("TransactionFactory [{}] was not registered " +
                    "for synchronization because synchronization is not active", txFactory);
        }
    }

    @NotNull
    public static DataSourceType determineDataSourceType() {
        DataSourceType type = DataSourceHolder.getCurDataSource();
        if (type == null) type = DataSourceType.MYSQL;
        return type;
    }
}
