package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author lxh
 * @date 2022/6/6-下午10:06
 */
public abstract class AbstractEntity {
    private String createdUser;

    private Date createdTime;

    private String updatedUser;

    private Date updatedTime;

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
