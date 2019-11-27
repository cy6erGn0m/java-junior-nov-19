package ru.levelp.tests;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantNotifications {
    private int messageCount = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition messageCountChanged = lock.newCondition();

    public void waitForNew(int lastSeenCount) throws InterruptedException {
        lock.lock();
        while (true) {
            if (messageCount > lastSeenCount) break;
            messageCountChanged.await();
        }
        lock.unlock();
    }

    public void postMessage() {
        lock.lock();
        messageCount++;
        messageCountChanged.signalAll();
        lock.unlock();
    }
}
