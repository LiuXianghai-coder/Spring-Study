package org.xhliu.service.proxy;

import org.xhliu.service.MineSubject;

public class MineSubjectProxy implements MineSubject {
    private final MineSubject mineSubject;

    public MineSubjectProxy(MineSubject mineSubject) {
        this.mineSubject = mineSubject;
    }

    public String getMessage() {
        before();
        String message = mineSubject.getMessage();
        System.out.println("Get Message: " + message);
        after();

        return message;
    }

    private void before() {
        System.out.println("Before Method invoke.....");
    }

    private void after() {
        System.out.println("After Method invoke.....");
    }
}
