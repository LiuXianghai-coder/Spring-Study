package org.xhliu.springtransaction.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author lxh
 */
@TableName("user_info")
public class UserInfo {

    @TableId
    @TableField("user_id")
    private String userId;

    @TableField("user_name")
    private String userName;

    @TableField("user_gender")
    private String userGender;

    @TableField("simple_id")
    private String simpleId;

    @TableField("backup_id")
    private String backUpId;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getSimpleId() {
        return simpleId;
    }

    public String getBackUpId() {
        return backUpId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public void setSimpleId(String simpleId) {
        this.simpleId = simpleId;
    }

    public void setBackUpId(String backUpId) {
        this.backUpId = backUpId;
    }
}
