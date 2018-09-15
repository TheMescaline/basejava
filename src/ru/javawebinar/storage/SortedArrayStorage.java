package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

/**
 * Storage for Resumes based on a SortedArray
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void insert(Resume resume, int index) {
        int actualIndex = -(index + 1);
        if (actualStorageSize - actualIndex > 0) {
            System.arraycopy(storage, actualIndex, storage, actualIndex + 1, actualStorageSize - actualIndex);
        } else  if (actualStorageSize - actualIndex == 0) {
            //no need to use System.arraycopy()
        } else {
            //Why? Something will be probably fucked up in the future, that's why.
            return;
        }
        storage[actualIndex] = resume;
    }

    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, actualStorageSize, new Resume(uuid));
    }

    @Override
    protected void deleteByIndex(int index) {
        if (index < actualStorageSize - 1) {
            System.arraycopy(storage, index + 1, storage, index, actualStorageSize - index - 1);
        }
    }
}
