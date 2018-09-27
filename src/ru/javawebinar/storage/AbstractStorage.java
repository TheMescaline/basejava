package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract void saveResume(Resume resume, Object pointer);

    protected abstract void updateResume(Resume resume, Object pointer);

    protected abstract Resume getResume(Object pointer);

    protected abstract void deleteResume(Object pointer);

    protected abstract Object getIndex(String uuid);

    protected abstract boolean indexChecker(Object index);

    @Override
    public void save(Resume resume) {
        Object pointer = getPointerIfNotExist(resume.getUuid());
        saveResume(resume, pointer);
    }

    @Override
    public void update(Resume resume) {
        Object pointer = getPointerIfExist(resume.getUuid());
        updateResume(resume, pointer);
    }

    @Override
    public Resume get(String uuid) {
        Object pointer = getPointerIfExist(uuid);
        return getResume(pointer);
    }

    @Override
    public void delete(String uuid) {
        Object pointer = getPointerIfExist(uuid);
        deleteResume(pointer);
    }

    private void ExistException(String uuid) {
        throw new ExistException(uuid);
    }

    private void NotExistException(String uuid) {
        throw new NotExistException(uuid);
    }

    private Object getPointerIfNotExist(String uuid) {
        Object index = getIndex(uuid);
        if (indexChecker(index)) {
            ExistException(uuid);
        }
        return index;
    }

    private Object getPointerIfExist(String uuid) {
        Object index = getIndex(uuid);
        if (!indexChecker(index)) {
            NotExistException(uuid);
        }
        return index;
    }
}
