package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    public void save(Resume resume) {
        if (actualStorageSize == STORAGE_LIMIT) {
            System.out.println("Error! Storage is full!");
        } else {
            int index = getIndex(resume.getUuid());
            if (index != -1) {
                System.out.println("Error! This resume is already in the storage!");
            } else {
                storage[actualStorageSize] = resume;
                actualStorageSize++;
            }
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            System.out.println("Error! This resume is not exist!");
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[actualStorageSize - 1];
            storage[actualStorageSize - 1] = null;
            actualStorageSize--;
        } else {
            System.out.println("Error! This resume is not exist!");
        }
    }

    protected int getIndex(String uuid) {
        int searchingIndex = -1;
        for (int i = 0; i < actualStorageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                searchingIndex = i;
                break;
            }
        }
        return searchingIndex;
    }
}
