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
    @Column(name = "id")
    private long id;

    @Column(name = "user_name")
    private String name;

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
