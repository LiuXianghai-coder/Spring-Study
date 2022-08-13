package com.example.demo.entity;

import java.util.Objects;

/**
 * @author lxh
 * @date 2022/6/6-下午10:09
 */
public class UserInfo extends AbstractEntity{
    private long id;

    private String name;

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
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", create_user=" + getCreatedUser() +
                ", create_time=" + getCreatedTime() +
                ", update_user=" + getUpdatedUser() +
                ", update_time=" + getUpdatedTime() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return id == userInfo.id && age == userInfo.age && Objects.equals(name, userInfo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
