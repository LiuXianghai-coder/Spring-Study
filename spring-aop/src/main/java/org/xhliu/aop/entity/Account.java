package org.xhliu.aop.entity;

public class Account {
    int balance = 20;

    public boolean withDraw(int amount) {
        if (balance < amount) return false;
        balance -= amount;

        return true;
    }
}
