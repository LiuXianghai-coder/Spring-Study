package org.xhliu.aop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xhliu.aop.entity.Account;

public class AccountTest {
    private Account account;

    @Before
    public void before() {
        account = new Account();
    }

    @Test
    public void success() {
        Assert.assertTrue(account.withDraw(5));
    }

    public void failed() {
        Assert.assertFalse(account.withDraw(100));
    }
}
