package ru.levelp.tests;

public class Notifications {
    private int messageCount = 0;
    private final Object messageCountChanged = new Object();

    public void waitForNew(int lastSeenCount) throws InterruptedException {
        synchronized (messageCountChanged) {
            while (true) {
                if (messageCount > lastSeenCount) break;
                messageCountChanged.wait();
            }
        }
    }

    public void postMessage() {
        synchronized (messageCountChanged) {
            messageCount++;
            messageCountChanged.notifyAll();
        }
    }
}
