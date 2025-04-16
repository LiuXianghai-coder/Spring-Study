package com.example.mybatisplusdemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 *@author lxh
 */
@TableName("user_info")
public class UserInfo {

    @TableId("user_id")
    private Integer userId;

    @TableField("user_name")
    private String userName;

    @TableField("user_gender")
    private String userGender;

    @TableField("simple_id")
    private String simpleId;

    @TableField("backup_id")
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
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userGender='" + userGender + '\'' +
                ", simpleId='" + simpleId + '\'' +
                ", backupId='" + backupId + '\'' +
                '}';
    }
}
