package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract void saveResume(Resume resume, Object pointer);

    protected abstract void updateResume(Resume resume, Object pointer);

    protected abstract Resume getResume(Object pointer);

    protected abstract void setUpStorage(Object pointer);

    protected abstract void clearStorage();

    protected abstract boolean isStorageNotFull(Resume resume);

    protected abstract Object getPointerIfNotExist(Resume resume);

    protected abstract Object getPointerIfExist(Resume resume);

    @Override
    public void save(Resume resume) {
        if (isStorageNotFull(resume)) {
            Object index = getPointerIfNotExist(resume);
            saveResume(resume, index);
        }
    }

    @Override
    public void update(Resume resume) {
        Object pointer = getPointerIfExist(resume);
        updateResume(resume, pointer);
    }

    @Override
    public void delete(String uuid) {
        Object pointer = getPointerIfExist(new Resume(uuid));
        setUpStorage(pointer);
        clearStorage();
    }

    @Override
    public Resume get(String uuid) {
        Object pointer = getPointerIfExist(new Resume(uuid));
        return getResume(pointer);
    }
}
