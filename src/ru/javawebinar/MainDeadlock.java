package ru.javawebinar;

public class MainDeadlock {
    public static final Object LOCK1 = new Object();
    public static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> doubleLock(LOCK1, LOCK2));
        Thread thread2 = new Thread(() -> doubleLock(LOCK2, LOCK1));
        thread1.start();
        thread2.start();
    }

    public static void doubleLock(Object firstLock, Object secondLock) {
        synchronized (firstLock) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (secondLock) {
            }
        }
    }
}
