package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author lxh
 * @date 2022/6/6-下午10:06
 */
public abstract class AbstractEntity {
    private String createdUser;

    private LocalDate createdTime;

    private String updatedUser;

    private LocalDate updatedTime;

    public void initFiled() {
        setCreatedTime(LocalDate.now());
        setUpdatedTime(LocalDate.now());
        setUpdatedUser("xhliu");
        setCreatedUser("xhliu");
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public LocalDate getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDate createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public LocalDate getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDate updatedTime) {
        this.updatedTime = updatedTime;
    }
}
