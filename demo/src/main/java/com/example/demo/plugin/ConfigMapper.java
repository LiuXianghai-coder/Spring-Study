package com.example.demo.plugin;

/**
 * @author lxh
 */
public class ConfigMapper {
    public String selectByUserId(String userId) {
        return "SELECT * FROM user_info WHERE user_id=" + userId;
    }
}
