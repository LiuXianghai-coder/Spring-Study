package com.example.demo.entity;

import java.util.List;

/**
 * @author xhliu
 */
public class UserInfoView
        extends UserInfo {

    private List<String> friends;

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String toString() {
        return "UserInfoView{" +
                super.toString() +  ", " +
                "friends=" + friends +
                '}';
    }
}
