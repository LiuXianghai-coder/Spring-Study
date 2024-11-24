package com.example.springtest.entity;

import java.util.Objects;

/**
 *@author lxh
 */
public class UserInfo {

    private Integer userId;

    private String userName;

    private String userGender;

    private String simpleId;

    private String backupId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getSimpleId() {
        return simpleId;
    }

    public void setSimpleId(String simpleId) {
        this.simpleId = simpleId;
    }

    public String getBackupId() {
        return backupId;
    }

    public void setBackupId(String backupId) {
        this.backupId = backupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(userId, userInfo.userId)
                && Objects.equals(userName, userInfo.userName)
                && Objects.equals(userGender, userInfo.userGender)
                && Objects.equals(simpleId, userInfo.simpleId)
                && Objects.equals(backupId, userInfo.backupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userGender, simpleId, backupId);
    }

    @Override
    public String toString() {
        return "UserInfo@" + Integer.toHexString(super.hashCode())
                + "{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userGender='" + userGender + '\'' +
                ", simpleId='" + simpleId + '\'' +
                ", backupId='" + backupId + '\'' +
                '}';
    }
}
