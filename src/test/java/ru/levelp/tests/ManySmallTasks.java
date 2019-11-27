package ru.levelp.tests;

import java.util.ArrayList;
import java.util.concurrent.*;

public class ManySmallTasks {
    // thread pool

    class Task {
        int a, b;
        int result;
    }

    private final BlockingQueue<Task> pending = new ArrayBlockingQueue<>(100);
    private final BlockingQueue<Task> pending2 = new LinkedBlockingQueue<>();
    private final ArrayList<Task> completed = new ArrayList<>();

//    private ExecutorService executor = Executors.newFixedThreadPool(100);
//    private ExecutorService executor = Executors.newCachedThreadPool();
//    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);

    public void submitTasks2() throws ExecutionException, InterruptedException, TimeoutException {
        for (int i = 0; i < 1000_000; ++i) {
            Task task = new Task();
            task.a = i;
            task.b = i + 10;

            Future<?> promise = executor.submit(() -> {
                processTask(task);
            });

            Future<Integer> promise2 = executor.submit(() -> task.a + task.b);

            promise.cancel(true);
            promise.get(); // wait until result
            promise.get(1, TimeUnit.SECONDS); // wait until result or timeout

            promise.isDone();
        }

        executor.schedule(() -> {
            System.out.println("After 5 seconds");
        }, 5, TimeUnit.SECONDS);

        Future<?> schedule = executor.scheduleAtFixedRate(() -> {
            System.out.println("Every 10 seconds");
        }, 1, 10, TimeUnit.SECONDS);

        schedule.cancel(false);
        schedule.get();
    }

    public void submitTasksOld() throws InterruptedException {
        for (int i = 0; i < 1000_000; ++i) {
            Task task = new Task();
            task.a = i;
            task.b = i + 10;
            pending.put(task);
        }
    }

    public void process() throws InterruptedException {
        while (true) {
            Task task = pending.take();

            new Thread(() -> {
                processTask(task);
                completed.add(task);
            }).start();
        }
    }

    public void processTask(Task task) {
        task.result = task.a + task.b;
    }
}
