package org.xhliu.springtransaction.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.xhliu.springtransaction.transaction.DynamicDataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TransactionConfiguration {

    @Bean(name = "dynamicDataSourceTransactionManager")
    public DataSourceTransactionManager dynamicDataSourceTransactionManager(
            @Qualifier("dynamicDataSource") DataSource dataSource,
            ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers
    ) {
        DynamicDataSourceTransactionManager transactionManager = new DynamicDataSourceTransactionManager(dataSource);
        transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
        return transactionManager;
    }
}
