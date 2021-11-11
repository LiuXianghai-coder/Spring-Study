package org.xhliu.aop.entity;

public aspect AccountAspect {
    final int MIN_BALANCE = 10;

    pointcut callWithDraw(int amount, Account acc):
            call(boolean Account.withdraw(int)) && args(amount) && target(acc);

    before(int amount, Account acc): callWithDraw(amount, acc) {
        System.out.println("[AccountAspect] 付款前总额: " + acc.balance);
        System.out.println("[AccountAspect] 需要付款: " + amount);
    }

    boolean around(int amount, Account acc):
            callWithDraw(amount, acc) {
        if (acc.balance < amount) {
            System.out.println("[AccountAspect] 拒绝付款！");
            return false;
        }
        return proceed(amount, acc);
    }

    after(int amount, Account balance): callWithDraw(amount, balance) {
        System.out.println("[AccountAspect] 付款后剩余：" + balance.balance);
    }
}
