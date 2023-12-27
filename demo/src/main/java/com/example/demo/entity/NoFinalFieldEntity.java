package com.example.demo.entity;

/**
 * @author lxh
 */
public class NoFinalFieldEntity {
    public UserInfo userInfo;

    public NoFinalFieldEntity() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        UserInfo tmp = new UserInfo();
        tmp.setId(1L);tmp.setName("xhliu2");
        this.userInfo = tmp;
    }
}
