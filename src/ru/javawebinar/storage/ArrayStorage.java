package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[actualStorageSize - 1];
            storage[actualStorageSize - 1] = null;
            actualStorageSize--;
        } else {
            System.out.println("Error! This resume is not exist!");
        }
    }

    @Override
    protected void insert(Resume resume, int index) {
        storage[actualStorageSize] = resume;
        actualStorageSize++;
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
}
