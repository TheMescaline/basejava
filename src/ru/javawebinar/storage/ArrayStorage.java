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
        int searchingIndex = -1;
        for (int i = 0; i < actualStorageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                searchingIndex = i;
                break;
            }
        }
        return searchingIndex;
    }

    @Override
    protected void deleteByIndex(int index) {
        storage[index] = storage[actualStorageSize - 1];
    }
}
