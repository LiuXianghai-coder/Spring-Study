package com.example.demo.transaction;

public final class DataSourceHolder {

    private final static ThreadLocal<String> DATASOURCE_HOLDER = new ThreadLocal<>();

    public static String getCurDataSource() {
        return DATASOURCE_HOLDER.get() == null ? "mysql" : DATASOURCE_HOLDER.get();
    }

    public static void setCurDataSource(String curDataSource) {
        DATASOURCE_HOLDER.set(curDataSource);
    }
}
