package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void insert(Resume resume, int index) {
        int actualIndex = -(index + 1);
        System.arraycopy(storage, actualIndex, storage, actualIndex + 1, actualStorageSize - actualIndex);
        storage[actualIndex] = resume;
    }

    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, actualStorageSize, new Resume(uuid));
    }
}
