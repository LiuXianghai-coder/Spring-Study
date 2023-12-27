package org.xhliu.springtransaction.transaction;

import org.apache.ibatis.transaction.TransactionFactory;
import org.springframework.transaction.support.ResourceHolderSupport;

import javax.sql.DataSource;

/**
 * @author lxh
 */
public class DynamicDataSourceTransactionHolder
        extends ResourceHolderSupport {

    private final TransactionFactory txFactory;

    private final DynamicTransaction tx;

    private final DataSource dataSource;

    public DynamicDataSourceTransactionHolder(TransactionFactory txFactory,
                                               DynamicTransaction tx,
                                               DataSource dataSource) {
        this.txFactory = txFactory;
        this.tx = tx;
        this.dataSource = dataSource;
    }

    public DynamicTransaction getTx() {
        return tx;
    }

    public TransactionFactory getTxFactory() {
        return txFactory;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
