package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 100;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int actualStorageSize = 0;

    protected abstract void doSave(Resume resume, Integer index);

    protected abstract void doDelete(int index);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, actualStorageSize, null);
        actualStorageSize = 0;
    }

    @Override
    protected List<Resume> getList() {
        return Arrays.asList(Arrays.copyOf(storage, actualStorageSize));
    }

    @Override
    public int size() {
        return actualStorageSize;
    }

    @Override
    protected void saveResume(Resume resume, Integer index) {
        if (actualStorageSize == STORAGE_LIMIT) {
            throw new StorageException("Error! Storage is full!", resume.getUuid());
        }
        doSave(resume, index);
        actualStorageSize++;
    }

    @Override
    protected void updateResume(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected void deleteResume(Integer index) {
        doDelete(index);
        storage[actualStorageSize - 1] = null;
        actualStorageSize--;
    }

    @Override
    protected Resume getResume(Integer index) {
        return storage[index];
    }

    @Override
    protected boolean pointerChecker(Integer index) {
        return index >= 0;
    }
}
