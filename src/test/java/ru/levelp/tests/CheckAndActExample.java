package ru.levelp.tests;

public class CheckAndActExample {
    private volatile boolean mayAct = true;
    private int counter = 0;

    public void act() {
        if (mayAct) {
            counter++;
        }
    }

    public void notAllow() {
        mayAct = false;
        counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public void thread2() {
        notAllow();
//        new Thread(() -> act()).start();// + join
        System.out.println("counter = " + counter);
    }


    public void x() {
        int a, b;

        a = 1;
        b = 2;

        a = a + 1;
        b = b + 1;
    }

//    public void notAllow2() {
    //        counter = 0;
    //        mayAct = false;
    //    }


    // counter = 1

    public void notAllow1() {
        counter = -1; // happens before
        mayAct = false;
//        counter = 0;
    }

    public void print() {
        if (mayAct) { // true
            System.out.println("counter = " + counter); // 1, -1
        } else {
            System.out.println("counter = " + counter); // -1
        }
    }





    // counter = 1
    public void happensBefore2() {
        synchronized (this) {
            counter = 0;
//            a = 1;
//            b = 2;
        }
    }

    public void thread2HappensBefore() {
        synchronized (this) {
            System.out.println("counter = " + counter);
        }
    }













    public void happensBeforeStartThread() {
        counter = 1;
        new Thread(() -> {
            System.out.println("counter = " + counter);
//            System.out.println("b = " + b);
        }).start();
//        b = 1;
    }










    class Cache {
        C cachedC;
    }

    class C {
        int a = 1;
        int b = 2;
        C other = new C();
    }

    public void createAndPublish(Cache cache) {
        C instance = new C();
//        instance.a = 1;
//        instance.b = 2;
        cache.cachedC = instance;
    }

    public void otherThread(Cache cache) {
        C instance = cache.cachedC;
        System.out.println(instance.a); // 1
        System.out.println(instance.b); // 2, 0
    }
}
