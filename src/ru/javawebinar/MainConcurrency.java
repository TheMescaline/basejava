package ru.javawebinar;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static int counter;
    private static final int THREADS_NUMBER = 10_000;
    private static final Lock LOCK = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread testThread = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        testThread.start();
        new Thread(() -> {
            Thread currentThread = Thread.currentThread();
            System.out.println(currentThread.getName() + ", " + currentThread.getState());
        }).start();
        System.out.println(testThread.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();

        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < THREADS_NUMBER; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.increment();
                }
                latch.countDown();
            });

        }

        latch.await();
        executorService.shutdown();
        System.out.println(counter);
    }

    private void increment() {
        LOCK.lock();
        counter++;
        LOCK.unlock();
    }
}
