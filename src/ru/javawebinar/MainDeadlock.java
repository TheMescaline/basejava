package ru.javawebinar;

public class MainDeadlock {
    public static final Object LOCK1 = new Object();
    public static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (LOCK1) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK2) {
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (LOCK2) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK1) {
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}
