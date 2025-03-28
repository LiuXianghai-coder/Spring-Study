package org.xhliu.sharding.entity;

import java.time.LocalDateTime;

/**
 *@author lxh
 */
public class OaStatistic {

    private Long id;

    private String statisticContent;

    private String createdId;

    private LocalDateTime createdTime;

    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatisticContent() {
        return statisticContent;
    }

    public void setStatisticContent(String statisticContent) {
        this.statisticContent = statisticContent;
    }

    public String getCreatedId() {
        return createdId;
    }

    public void setCreatedId(String createdId) {
        this.createdId = createdId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
