package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
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
    protected void saveResume(Resume resume, Object index) {
        storage[actualStorageSize] = resume;
        actualStorageSize++;
    }

    @Override
    protected void setUpStorage(Object pointer) {
        int index = (int) pointer;
        storage[index] = storage[actualStorageSize - 1];
    }
}
