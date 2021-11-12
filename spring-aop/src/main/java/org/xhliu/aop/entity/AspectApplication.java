package org.xhliu.aop.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.aopshare.entity.UserAccount;
/**
 * @author xhliu2
 * @create 2021-11-12 9:43
 **/
public class AspectApplication {
    private final static Logger log = LoggerFactory.getLogger(AspectApplication.class);

    public static void main(String[] args) {
        UserAccount account = new UserAccount();
//        Account account = new Account();
        log.info("================ 分割线 ==================");
//        account.withDraw(10);
//        account.withDraw(100);
        account.withDraw(10);
        account.withDraw(100);
        log.info("================ 结束 ==================");
    }
}
