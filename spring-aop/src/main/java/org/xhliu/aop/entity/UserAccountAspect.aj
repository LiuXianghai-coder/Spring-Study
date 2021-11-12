package org.xhliu.aop.entity;

import com.example.aopshare.entity.UserAccount;

public aspect UserAccountAspect {
    pointcut callWithDraw(int amount, UserAccount acc):
            call(boolean UserAccount.withDraw(int)) && args(amount) && target(acc);

    before(int amount, UserAccount acc): callWithDraw(amount, acc) {
        System.out.println("[UserAccountAspect] 付款前总额: " + acc.getBalance());
        System.out.println("[UserAccountAspect] 需要付款: " + amount);
    }

    boolean around(int amount, UserAccount acc):
            callWithDraw(amount, acc) {
        if (acc.getBalance() < amount) {
            System.out.println("[UserAccountAspect] 拒绝付款！");
            return false;
        }
        return proceed(amount, acc);
    }

    after(int amount, UserAccount balance): callWithDraw(amount, balance) {
        System.out.println("[UserAccountAspect] 付款后剩余：" + balance.getBalance());
    }
}
