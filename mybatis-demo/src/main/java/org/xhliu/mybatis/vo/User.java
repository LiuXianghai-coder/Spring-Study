package org.xhliu.mybatis.vo;

import java.util.List;

/**
 * @author xhliu
 * @time 2022-03-02-下午9:06
 */
public class User {
    private long id;
    private String name;
    private int age;
    private List<UserContact> userContacts;

    public List<UserContact> getUserContacts() {
        return userContacts;
    }

    public void setUserContacts(List<UserContact> userContacts) {
        this.userContacts = userContacts;
    }

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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
