package com.example.demo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class H2O {

    private final Semaphore oxSemaphore = new Semaphore(1);
    private final Semaphore hydSemaphore = new Semaphore(0);
    private final Lock lock = new ReentrantLock();

    private int hCnt = 0;

    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        oxSemaphore.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();

        hydSemaphore.release();
        hydSemaphore.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        hydSemaphore.acquire();
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();

        lock.lock();
        try {
            hCnt = (hCnt + 1) % 2;
            if (hCnt == 0) {
                oxSemaphore.release();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
    }
}
