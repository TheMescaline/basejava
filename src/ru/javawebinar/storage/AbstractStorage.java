package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract void saveResume(Resume resume, Object pointer);

    protected abstract void updateResume(Resume resume, Object pointer);

    protected abstract Resume getResume(Object pointer);

    protected abstract void deleteResume(Object pointer);

    protected abstract Object getPointerIfNotExist(String uuid);

    protected abstract Object getPointerIfExist(String uuid);

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

    protected void ExistException(String uuid) {
        throw new ExistException(uuid);
    }

    protected void NotExistException(String uuid) {
        throw new NotExistException(uuid);
    }
}
