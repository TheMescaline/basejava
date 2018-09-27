package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 100;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int actualStorageSize = 0;

    protected abstract void doSave(Resume resume, Object pointer);

    protected abstract void doDelete(int index);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, actualStorageSize, null);
        actualStorageSize = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, actualStorageSize);
    }

    @Override
    public int size() {
        return actualStorageSize;
    }

    @Override
    protected void saveResume(Resume resume, Object pointer) {
        if (actualStorageSize == STORAGE_LIMIT) {
            throw new StorageException("Error! Storage is full!", resume.getUuid());
        }
        doSave(resume, pointer);
        actualStorageSize++;
    }

    @Override
    protected void updateResume(Resume resume, Object pointer) {
        int index = (int) pointer;
        storage[index] = resume;
    }

    @Override
    protected void deleteResume(Object pointer) {
        int index = (int) pointer;
        doDelete(index);
        storage[actualStorageSize - 1] = null;
        actualStorageSize--;
    }

    @Override
    protected Resume getResume(Object pointer) {
        int index = (int) pointer;
        return storage[index];
    }

    @Override
    protected boolean notExistChecker(Object index) {
        return (int) index >= 0;
    }

    @Override
    protected boolean existChecker(Object index) {
        return (int) index < 0;
    }
}
