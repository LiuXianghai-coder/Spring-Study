package com.example.demo.entity;

import com.google.common.base.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equal(createdUser, that.createdUser)
                && Objects.equal(createdTime, that.createdTime)
                && Objects.equal(updatedUser, that.updatedUser)
                && Objects.equal(updatedTime, that.updatedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(createdUser, createdTime, updatedUser, updatedTime);
    }

    @Override
    public String toString() {
        return  ", createdUser='" + createdUser + '\'' +
                ", createdTime=" + createdTime +
                ", updatedUser='" + updatedUser + '\'' +
                ", updatedTime=" + updatedTime;
    }
}
