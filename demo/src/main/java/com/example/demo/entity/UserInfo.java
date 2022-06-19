package com.example.demo.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

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

//    @Override
//    public String toString() {
//        return MoreObjects.toStringHelper(this)
//                .add("id", id)
//                .add("name", name)
//                .add("age", age)
//                .add("create_user", getCreatedUser())
//                .add("create_time", getCreatedTime())
//                .add("update_user", getUpdatedUser())
//                .add("update_time", getUpdatedTime())
//                .toString();
//    }


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
}
