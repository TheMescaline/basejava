package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Error! This resume is not exist!");
        } else {
            for (int i = index; i < actualStorageSize; i++) {
                storage[i] = storage[i + 1];
            }
            actualStorageSize--;
        }
    }

    @Override
    protected void insert(Resume resume, int index) {
        for (int i = actualStorageSize; i > -(index + 1); i--) {
            storage[i] = storage[i - 1];
        }
        storage[-(index + 1)] = resume;
        actualStorageSize++;
    }

    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, actualStorageSize, new Resume(uuid));
    }
}
