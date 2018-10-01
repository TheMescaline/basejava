package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import java.util.Arrays;
import java.util.List;

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
    public List<Resume> getAllSorted() {
        List<Resume> result = Arrays.asList(Arrays.copyOf(storage, actualStorageSize));
        result.sort(RESUME_COMPARATOR);
        return result;
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
    protected boolean indexChecker(Object index) {
        return (int) index >= 0;
    }
}
