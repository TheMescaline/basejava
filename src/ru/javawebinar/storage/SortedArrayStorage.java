package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume resume) {

    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void delete(String uuid) {
    }

    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, actualStorageSize, new Resume(uuid));
    }
}
