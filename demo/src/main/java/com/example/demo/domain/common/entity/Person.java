package com.example.demo.domain.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author lxh
 * @date 2022/6/19-下午8:39
 */
public class Person {
    private String name;
    private Date createdTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}