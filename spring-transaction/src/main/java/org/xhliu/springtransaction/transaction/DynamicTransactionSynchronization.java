package org.xhliu.springtransaction.transaction;

import org.apache.ibatis.transaction.TransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.SQLException;

/**
 * @author lxh
 */
public class DynamicTransactionSynchronization
        implements TransactionSynchronization {

    private final static Logger log = LoggerFactory.getLogger(DynamicTransactionSynchronization.class);

    private final DynamicDataSourceTransactionHolder holder;

    private final TransactionFactory txFactory;

    private boolean holderActive = true;

    public DynamicTransactionSynchronization(DynamicDataSourceTransactionHolder holder,
                                             TransactionFactory txFactory) {
        this.holder = holder;
        this.txFactory = txFactory;
    }

    @Override
    public void suspend() {
        if (this.holderActive) {
            log.debug("Transaction synchronization suspending TransactionFactory [" + this.holder.getTxFactory() + "]");
            TransactionSynchronizationManager.unbindResource(this.txFactory);
        }
    }

    @Override
    public void resume() {
        if (this.holderActive) {
            log.debug("Transaction synchronization resuming TransactionFactory [" + this.holder.getTxFactory() + "]");
            TransactionSynchronizationManager.bindResource(this.txFactory, this.holder);
        }
    }

    @Override
    public void afterCommit() {
        try {
            holder.getTx().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void beforeCompletion() {
        if (!this.holder.isOpen()) {
            log.debug("Transaction synchronization deregister TransactionFactory [" + this.holder.getTxFactory() + "]");
            TransactionSynchronizationManager.unbindResource(txFactory);
            this.holderActive = false;
            log.debug("Transaction synchronization closing TransactionFactory [" + this.holder.getTxFactory() + "]");
        }
    }

    @Override
    public void afterCompletion(int status) {
        DynamicTransaction tx = holder.getTx();
        try {
            tx.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
