package com.example.demo.domain.ed.entity;

import java.time.LocalDateTime;

public class ProjectInfo {
    private String projId;

    private String name;

    private LocalDateTime updateTime;

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ProjectInfo{" +
                "projId='" + projId + '\'' +
                ", name='" + name + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
