package ru.levelp.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
//    private final Object counterLock = new Object();
    private final ReentrantLock counterLock = new ReentrantLock();
    private volatile long counter;

//    private final Object createdAccountsLock = new Object();
//    private volatile long createdAccounts;

    public void onRequest() {
        counterLock.lock();
        try {
            counter++;
        } finally {
            counterLock.unlock();
        }
    }

    public long getRequestCount() {
        return counter;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        List<Thread> started = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    counter.onRequest();
                }
            });
            thread.start();
            started.add(thread);
        }

        for (Thread thread : started) {
            thread.join();
        }

        System.out.println("Counter: " + counter.getRequestCount());
    }
}
