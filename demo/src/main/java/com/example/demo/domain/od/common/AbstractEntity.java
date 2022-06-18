package com.example.demo.domain.od.common;

import java.time.LocalDateTime;

/**
 * All Entity include fields
 *
 * @author lxh
 * @date 2022/6/18-下午8:08
 */
public class AbstractEntity {
    private String lDel; // delete flag

    private String vcCreatedUser; // created user's name

    private String vcUpdatedUser; // updated user's name

    private LocalDateTime createdTime; // created time

    private LocalDateTime updatedTime; // updated time

    public String getlDel() {
        return lDel;
    }

    public void setlDel(String lDel) {
        this.lDel = lDel;
    }

    public String getVcCreatedUser() {
        return vcCreatedUser;
    }

    public void setVcCreatedUser(String vcCreatedUser) {
        this.vcCreatedUser = vcCreatedUser;
    }

    public String getVcUpdatedUser() {
        return vcUpdatedUser;
    }

    public void setVcUpdatedUser(String vcUpdatedUser) {
        this.vcUpdatedUser = vcUpdatedUser;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
