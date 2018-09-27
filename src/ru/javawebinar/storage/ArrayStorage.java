package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Object getIndex(String uuid) {
        for (int i = 0; i < actualStorageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Resume resume, Object pointer) {
        storage[actualStorageSize] = resume;
    }

    @Override
    protected void doDelete(int index) {
        storage[index] = storage[actualStorageSize - 1];
    }
}
