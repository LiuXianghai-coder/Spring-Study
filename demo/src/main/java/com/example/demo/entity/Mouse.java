package com.example.demo.entity;

import org.apache.commons.math3.primes.Primes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author xhliu2
 * @create 2021-09-29 9:04
 **/
public class Mouse {

    private final static Logger log = LoggerFactory.getLogger(Mouse.class);

    private Mouse instance = null;

    private String value;
    private int cnt;
    private InsuredEntity entity;

    public Mouse() {
        this.value = "mouse";
        cnt += 1;
    }

    public Mouse(int cnt, String str) {
        this.cnt += 1;
        this.value = str;
        entity = new InsuredEntity();
        log.info("Mouse Initialize");
    }

    public String getValue() {
        return value;
    }

    public int getCnt() {
        return cnt;
    }

    public Mouse getInstance() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = new Mouse(1, "str");
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        Thread[] ts = new Thread[200];
        Mouse mouse = new Mouse();
        CountDownLatch latch = new CountDownLatch(ts.length);
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Mouse inst = mouse.getInstance();
//                String s = inst.getValue();
//                int cnt = inst.getCnt();
//                System.out.println(s + "\t" + cnt);
            });
        }

        for (Thread t : ts) {
            t.start();
            latch.countDown();
        }
        System.out.println(mouse.getInstance().cnt);
    }
}
