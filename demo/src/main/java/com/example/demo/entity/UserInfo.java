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
public class UserInfo
        extends AbstractEntity
        implements TaskInfo {
    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_gender")
    private String gender;

    @Column(name = "user_age")
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public long userId() {
        return id;
    }

    @Override
    public String userName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
}
