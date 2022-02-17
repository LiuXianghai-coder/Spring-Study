package org.xhliu.guiceexample.moudle;

import com.google.inject.AbstractModule;
import org.xhliu.guiceexample.application.Application;
import org.xhliu.guiceexample.application.impl.GuiceApplication;
import org.xhliu.guiceexample.service.LogService;
import org.xhliu.guiceexample.service.UserService;
import org.xhliu.guiceexample.service.impl.LogServiceImpl;
import org.xhliu.guiceexample.service.impl.UserServiceImpl;

/**
 * @author xhliu
 * @create 2022-02-17-15:04
 **/
public class MineAppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LogService.class).to(LogServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(Application.class).to(GuiceApplication.class);
    }
}
