package com.example.restdata.Entity;

import lombok.Data;

import javax.persistence.*;
import org.springframework.data.rest.core.annotation.RestResource;

/*
*   定义的基本用户类， 使用lombok的 @Data 注解
*   自动生成对应属性的Getter和Setter方法
 */
@Data
@Entity
@RestResource(rel = "userInfos", path = "userInfos")
@Table(name = "user_info")
public class UserInfo {
    @Id
    @Column(name = "user_id")
    // 用户的 Id 属性
    private Long userId;

    @Basic
    @Column(name = "user_name")
    // 用户名
    private String userName;

    @Basic
    @Column(name = "user_gender")
    // 用户性别
    private String userGender;
}

