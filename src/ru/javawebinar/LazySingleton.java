package ru.javawebinar;

public class LazySingleton {
    private LazySingleton() {
    }

    public static class LazySingletonHolder {
        public static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }
}
