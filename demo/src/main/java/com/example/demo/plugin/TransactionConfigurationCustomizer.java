package com.example.demo.plugin;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.TransactionFactory;
import tk.mybatis.mapper.autoconfigure.ConfigurationCustomizer;

import javax.sql.DataSource;

public class TransactionConfigurationCustomizer
        implements ConfigurationCustomizer {

    private final TransactionFactory txFactory;

    private final DataSource dataSource;

    public TransactionConfigurationCustomizer(TransactionFactory txFactory,
                                              DataSource dataSource) {
        this.txFactory = txFactory;
        this.dataSource = dataSource;
    }

    @Override
    public void customize(Configuration configuration) {
        Environment environment = configuration.getEnvironment();
        if (environment == null) {
            configuration.setEnvironment(new Environment(
                    TransactionConfigurationCustomizer.class.getSimpleName(),
                    txFactory, dataSource));
            return;
        }
        configuration.setEnvironment(new Environment.Builder(environment.getId())
                .dataSource(dataSource)
                .transactionFactory(txFactory)
                .build());
    }
}
