package com.example.demo.entity;

import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * @author xhliu
 * @create 2022-04-02-9:44
 **/
@Entity
@Table(name = "user_info", schema = "lxh_db", catalog = "")
public class UserInfo {
    private Long userId;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;

    @Id
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equal(userId, userInfo.userId)
                && Objects.equal(firstName, userInfo.firstName)
                && Objects.equal(lastName, userInfo.lastName)
                && Objects.equal(phone, userInfo.phone)
                && Objects.equal(gender, userInfo.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, firstName, lastName, phone, gender);
    }
}
