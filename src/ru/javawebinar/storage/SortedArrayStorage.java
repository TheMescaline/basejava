package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.Arrays;

/**
 * Storage for Resumes based on a SortedArray
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, actualStorageSize, new Resume(uuid));
    }

    @Override
    protected void doSave(Resume resume, Object pointer) {
        int insertionIndex = -((int) pointer + 1);
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, actualStorageSize - insertionIndex);
        storage[insertionIndex] = resume;
    }

    @Override
    protected void doDelete(int index) {
        int lengthOfCopiedArray = actualStorageSize - index - 1;
        if (lengthOfCopiedArray > 0) {
            System.arraycopy(storage, index + 1, storage, index, lengthOfCopiedArray);
        }
    }
}
