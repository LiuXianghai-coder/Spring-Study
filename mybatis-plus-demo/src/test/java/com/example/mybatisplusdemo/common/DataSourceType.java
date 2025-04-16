package com.example.mybatisplusdemo.common;

/**
 *@author lxh
 */
public enum DataSourceType {

    MASTER("master"),

    SLAVE("slave");

    public final String name;

    DataSourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
