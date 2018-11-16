package ru.javawebinar;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static int counter;
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

        List<Thread> list = new ArrayList<>();
        MainConcurrency mainConcurrency = new MainConcurrency();
        for (int i = 0; i < 10_000; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.increment();
                }
            });
            thread.start();
            list.add(thread);
        }

        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);
        LazySingleton.getInstance();
    }

    private synchronized void increment() {
        counter++;
    }
}
