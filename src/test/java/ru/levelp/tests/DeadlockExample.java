package ru.levelp.tests;

public class DeadlockExample {
    class Account {
        int id;
        volatile long balance;
    }

    // A, B
    // tx1: A -> B
    // tx2: B -> A

    public void transfer(Account sender, Account recipient, long amount) {
        if (amount < 0) throw new IllegalArgumentException("...");

        Object first, second;
        if (sender.id < recipient.id) {
            first = sender;
            second = recipient;
        } else {
            first = recipient;
            second = sender;
        }

        synchronized (first) { // tx1 A, tx2 B
            synchronized (second) { // tx1 B?, tx2 A?
                if (sender.balance < amount) throw new IllegalArgumentException("...");
                sender.balance -= amount;

                recipient.balance += amount;
            }
        }
    }
}
