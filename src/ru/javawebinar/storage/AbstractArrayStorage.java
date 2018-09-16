package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int actualStorageSize = 0;

    protected abstract void insert(Resume resume, int index);

    protected abstract int getIndex(String uuid);

    protected abstract void setUpStorage(int index);

    public void clear() {
        Arrays.fill(storage, 0, actualStorageSize, null);
        actualStorageSize = 0;
    }

    public void save(Resume resume) {
        if (actualStorageSize == STORAGE_LIMIT) {
            throw new StorageException("Error! Storage is full!", resume.getUuid());
        } else {
            int index = getIndex(resume.getUuid());
            if (index >= 0) {
                throw new ExistException(resume.getUuid());
            } else {
                insert(resume, index);
                actualStorageSize++;
            }
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistException(resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistException(uuid);
        } else {
            setUpStorage(index);
            storage[actualStorageSize - 1] = null;
            actualStorageSize--;
        }
    }

    public int size() {
        return actualStorageSize;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistException(uuid);
        }
        return storage[index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, actualStorageSize);
    }
}
