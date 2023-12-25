package org.xhliu.springtransaction.datasource;

/**
 * @author lxh
 */
public class DataSourceHolder {

    private static final ThreadLocal<DataSourceType> dataSourceHolder = new ThreadLocal<>();

    public static void setCurDataSource(DataSourceType type) {
        dataSourceHolder.set(type);
    }

    public static DataSourceType getCurDataSource() {
        return dataSourceHolder.get();
    }
}
