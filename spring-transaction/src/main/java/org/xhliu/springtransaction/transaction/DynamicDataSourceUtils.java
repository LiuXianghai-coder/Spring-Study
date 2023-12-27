package org.xhliu.springtransaction.transaction;

import org.apache.ibatis.transaction.TransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * @author lxh
 */
public class DynamicDataSourceUtils {

    private final static Logger log = LoggerFactory.getLogger(DynamicDataSourceUtils.class);

    public static DynamicTransaction getDynamicTransaction(TransactionFactory txFactory,
                                                           DataSource dataSource) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            Assert.notNull(txFactory, "查询的动态数据源的同步 key 不能为 null");
            DynamicDataSourceTransactionHolder holder =
                    (DynamicDataSourceTransactionHolder) TransactionSynchronizationManager.getResource(txFactory);
            DynamicTransaction transaction = txHolder(holder);
            if (transaction != null) {
                return transaction;
            }

            log.debug("Create new DynamicTransaction");
            transaction = new DynamicTransaction(dataSource, txFactory);

            registerTransactionHolder(txFactory, transaction, dataSource);

            return transaction;
        }

        return new DynamicTransaction(dataSource, txFactory);
    }

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
}
