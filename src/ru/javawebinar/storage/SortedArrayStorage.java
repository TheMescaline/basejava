package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Storage for Resumes based on a SortedArray
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> BINARY_SEARCH_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, actualStorageSize, new Resume(uuid, "Dummy"), BINARY_SEARCH_COMPARATOR);
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        int insertionIndex = -(index + 1);
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
