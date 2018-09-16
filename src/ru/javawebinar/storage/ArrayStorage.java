package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected void insert(Resume resume, int index) {
        storage[actualStorageSize] = resume;
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < actualStorageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void setUpStorage(int index) {
        storage[index] = storage[actualStorageSize - 1];
    }
}
