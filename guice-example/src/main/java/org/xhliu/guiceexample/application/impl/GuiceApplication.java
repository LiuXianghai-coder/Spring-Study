package org.xhliu.guiceexample.application.impl;

import com.google.inject.Inject;
import org.xhliu.guiceexample.application.Application;
import org.xhliu.guiceexample.service.LogService;
import org.xhliu.guiceexample.service.UserService;

/**
 * @author xhliu
 * @create 2022-02-17-15:01
 **/
public class GuiceApplication implements Application {
    private UserService userService;
    private LogService logService;

    @Inject
    public GuiceApplication(
            UserService userService,
            LogService logService
    ) {
        this.userService = userService;
        this.logService = logService;
    }

    @Override
    public void work() {
        userService.process();
        logService.log("正常运行。。。");
    }
}
