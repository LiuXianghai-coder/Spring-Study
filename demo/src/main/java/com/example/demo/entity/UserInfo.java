package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author lxh
 * @date 2022/6/6-下午10:09
 */
@Table(name = "user_info")
public class UserInfo {
    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_gender")
    private String gender;

    @Column(name = "simple_id")
    private String simpleId;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getSimpleId() {
        return simpleId;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSimpleId(String simpleId) {
        this.simpleId = simpleId;
    }
}
