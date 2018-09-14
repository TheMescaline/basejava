package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int actualStorageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, actualStorageSize, null);
        actualStorageSize = 0;
    }

    public int size() {
        return actualStorageSize;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Error! This resume is not exist!");
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, actualStorageSize);
    }
}
