package org.xhliu.springtransaction.config;

import com.baomidou.mybatisplus.autoconfigure.SqlSessionFactoryBeanCustomizer;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.transaction.TransactionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xhliu.springtransaction.transaction.DynamicTransactionFactory;

/**
 * @author lxh
 */
@Configuration
public class MyBatisConfig {

    @Bean(name = "dynamicTransactionFactory")
    public TransactionFactory dynamicTransactionFactory() {
        return new DynamicTransactionFactory();
    }

    @Bean(name = "dynamicDataSourceCustomizer")
    public SqlSessionFactoryBeanCustomizer
    dynamicDataSourceCustomizer(
            @Qualifier("dynamicTransactionFactory") TransactionFactory dynamicTransactionFactory
    ) {
        return new DynamicDataSourceCustomizer(dynamicTransactionFactory);
    }

    static class DynamicDataSourceCustomizer
            implements SqlSessionFactoryBeanCustomizer {

        private final TransactionFactory txFactory;

        DynamicDataSourceCustomizer(TransactionFactory txFactory) {
            this.txFactory = txFactory;
        }

        @Override
        public void customize(MybatisSqlSessionFactoryBean factoryBean) {
            factoryBean.setTransactionFactory(txFactory);
        }
    }
}
