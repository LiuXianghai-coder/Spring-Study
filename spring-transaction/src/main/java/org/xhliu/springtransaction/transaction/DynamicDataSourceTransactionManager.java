package org.xhliu.springtransaction.transaction;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DynamicDataSourceTransactionManager
        extends DataSourceTransactionManager {

    public DynamicDataSourceTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    @NotNull
    @Override
    protected DataSource obtainDataSource() {
        DataSource curDataSource = super.obtainDataSource();
        while (curDataSource instanceof DelegatingDataSource) {
            curDataSource = ((DelegatingDataSource) curDataSource).getTargetDataSource();
        }
        if (curDataSource instanceof AbstractRoutingDataSource) {
            Map<Object, DataSource> dss = ((AbstractRoutingDataSource) curDataSource).getResolvedDataSources();
            return dss.getOrDefault(DynamicDataSourceUtils.determineDataSourceType(),
                    ((AbstractRoutingDataSource) curDataSource).getResolvedDefaultDataSource());
        }
        assert curDataSource != null;
        return curDataSource;
    }
}
