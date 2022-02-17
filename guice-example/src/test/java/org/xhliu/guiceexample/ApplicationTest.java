package org.xhliu.guiceexample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xhliu.guiceexample.application.Application;
import org.xhliu.guiceexample.moudle.MineAppModule;

/**
 * @author xhliu
 * @create 2022-02-17-15:07
 **/
public class ApplicationTest {
    private Injector injector;

    @BeforeEach
    public void init() {
        injector = Guice.createInjector(new MineAppModule());
    }

    @Test
    public void testApp() {
        Application application = injector.getInstance(Application.class);
        application.work();
    }
}
