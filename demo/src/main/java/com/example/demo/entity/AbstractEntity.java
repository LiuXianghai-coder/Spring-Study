package com.example.demo.entity;

import cn.hutool.core.date.DateTime;
import com.google.common.base.Objects;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lxh
 */
public abstract class AbstractEntity implements Serializable {

    @Column(name = "delete_flag")
    private String deleteFlag;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_user")
    private String updatedUser;

    @Column(name = "updated_time")
    private Date updatedTime;

    public void initFiled() {
        setDeleteFlag("0");
        setCreatedTime(new DateTime());
        setUpdatedTime(new DateTime());
        setUpdatedUser("xhliu");
        setCreatedUser("xhliu");
    }

    public void initUpdate() {
        setUpdatedTime(new DateTime());
        setUpdatedUser("xhliu");
    }

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

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public void setUpdatedTime(Date updatedTime) {
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
