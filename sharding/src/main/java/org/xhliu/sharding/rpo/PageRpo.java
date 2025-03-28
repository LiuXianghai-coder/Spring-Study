package org.xhliu.sharding.rpo;

import java.util.Objects;

/**
 *@author lxh
 */
public abstract class PageRpo {

    protected static final int DEFAULT_PAGE_NO = 1;

    protected static final int DEFAULT_PAGE_SIZE = 10;

    private Integer pageNo;

    private Integer pageSize;

    public Integer getPageNo() {
        return Objects.isNull(pageNo) ? DEFAULT_PAGE_NO : pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return Objects.isNull(pageSize) ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
