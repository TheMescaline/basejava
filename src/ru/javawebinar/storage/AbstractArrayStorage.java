package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 100;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int actualStorageSize = 0;

    protected abstract int getIndex(String uuid);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, actualStorageSize, null);
        actualStorageSize = 0;
    }

    @Override
    public int size() {
        return actualStorageSize;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, actualStorageSize);
    }

    @Override
    protected void updateResume(Resume resume, Object pointer) {
        int index = (int) pointer;
        storage[index] = resume;
    }

    @Override
    protected Resume getResume(Object pointer) {
        int index = (int) pointer;
        return storage[index];
    }

    @Override
    protected void clearStorage() {
        storage[actualStorageSize - 1] = null;
        actualStorageSize--;
    }

    @Override
    protected boolean isStorageNotFull(Resume resume) {
        if (actualStorageSize == STORAGE_LIMIT) {
            throw new StorageException("Error! Storage is full!", resume.getUuid());
        }
        return true;
    }

    @Override
    protected Object getPointerIfNotExist(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistException(resume.getUuid());
        }
        return index;
    }

    @Override
    protected Object getPointerIfExist(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistException(resume.getUuid());
        }
        return index;
    }
}
