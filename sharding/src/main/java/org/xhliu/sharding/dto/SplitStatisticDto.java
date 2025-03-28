package org.xhliu.sharding.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *@author lxh
 */
public class SplitStatisticDto {

    private String tableName;

    private Long id;

    private LocalDateTime createdTime;

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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SplitStatisticDto that = (SplitStatisticDto) o;
        return Objects.equals(tableName, that.tableName)
                && Objects.equals(id, that.id)
                && Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, id, createdTime);
    }
}
