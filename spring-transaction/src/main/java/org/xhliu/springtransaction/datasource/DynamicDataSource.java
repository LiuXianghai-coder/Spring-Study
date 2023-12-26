package org.xhliu.springtransaction.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author lxh
 */
public class DynamicDataSource
        extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getCurDataSource();
    }
}
