package com.example.demo.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * @author lxh
 */
public abstract class AbstractEntity implements Serializable {

    private String createdUser;

    private OffsetDateTime createdTime;

    private String updatedUser;

    private OffsetDateTime updatedTime;

    public void initFiled() {
        setCreatedTime(OffsetDateTime.now());
        setUpdatedTime(OffsetDateTime.now());
        setUpdatedUser("xhliu");
        setCreatedUser("xhliu");
    }

    public void initUpdate() {
        setUpdatedTime(OffsetDateTime.now());
        setUpdatedUser("xhliu");
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public OffsetDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(OffsetDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public OffsetDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(OffsetDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return  ", createdUser='" + createdUser + '\'' +
                ", createdTime=" + createdTime +
                ", updatedUser='" + updatedUser + '\'' +
                ", updatedTime=" + updatedTime;
    }
}
