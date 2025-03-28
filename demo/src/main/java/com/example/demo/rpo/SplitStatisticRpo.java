package com.example.demo.rpo;

import org.springframework.beans.BeanUtils;

/**
 *@author lxh
 */
public class SplitStatisticRpo
        extends PageRpo {

    private String tableName;

    private Integer offset;

    private Integer maxLimit;

    private String condition;

    public Integer getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Integer maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public static SplitStatisticRpo valueOf(SplitStatisticRpo statisticRpo) {
        SplitStatisticRpo res = new SplitStatisticRpo();
        BeanUtils.copyProperties(statisticRpo, res);
        return res;
    }
}
