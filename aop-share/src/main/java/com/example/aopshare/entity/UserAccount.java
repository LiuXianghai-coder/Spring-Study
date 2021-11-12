package com.example.aopshare.entity;

public class UserAccount {
    int balance = 20;

    public boolean withDraw(int amount) {
        if (balance < amount) return false;
        balance -= amount;

        return true;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}

