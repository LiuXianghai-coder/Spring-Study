package com.example.demo.entity;

import java.util.Objects;
import java.util.UUID;

public class OaPenetrateTask {
    private String id;
    private String vcSecurityPoolId;
    private String vcPenetrated;
    private String vcTaskType;
    private String vcAllocState;
    private String vcTaskState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVcSecurityPoolId() {
        return vcSecurityPoolId;
    }

    public void setVcSecurityPoolId(String vcSecurityPoolId) {
        this.vcSecurityPoolId = vcSecurityPoolId;
    }

    public String getVcPenetrated() {
        return vcPenetrated;
    }

    public void setVcPenetrated(String vcPenetrated) {
        this.vcPenetrated = vcPenetrated;
    }

    public String getVcTaskType() {
        return vcTaskType;
    }

    public void setVcTaskType(String vcTaskType) {
        this.vcTaskType = vcTaskType;
    }

    public String getVcAllocState() {
        return vcAllocState;
    }

    public void setVcAllocState(String vcAllocState) {
        this.vcAllocState = vcAllocState;
    }

    public String getVcTaskState() {
        return vcTaskState;
    }

    public void setVcTaskState(String vcTaskState) {
        this.vcTaskState = vcTaskState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OaPenetrateTask that = (OaPenetrateTask) o;
        return Objects.equals(id, that.id)
                && Objects.equals(vcSecurityPoolId, that.vcSecurityPoolId)
                && Objects.equals(vcPenetrated, that.vcPenetrated)
                && Objects.equals(vcTaskType, that.vcTaskType)
                && Objects.equals(vcAllocState, that.vcAllocState)
                && Objects.equals(vcTaskState, that.vcTaskState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vcSecurityPoolId, vcPenetrated, vcTaskType, vcAllocState, vcTaskState);
    }

    public static OaPenetrateTask ofPenetrateData(String poolId) {
        OaPenetrateTask task = new OaPenetrateTask();
        task.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        task.setVcSecurityPoolId(poolId);
        task.setVcPenetrated("1");
        task.setVcTaskType("0");
        task.setVcAllocState("1");
        task.setVcTaskState("0");
        return task;
    }
}
