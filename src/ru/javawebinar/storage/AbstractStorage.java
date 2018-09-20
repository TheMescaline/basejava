package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void save(Resume resume) {
        checkExistException(resume.getUuid());
        saveResume(resume);
    }

    @Override
    public void update(Resume resume) {
        checkNotExistException(resume.getUuid());
        updateResume(resume);
    }

    @Override
    public Resume get(String uuid) {
        checkNotExistException(uuid);
        return getResume(uuid);
    }

    @Override
    public void delete(String uuid) {
        checkNotExistException(uuid);
        deleteResume(uuid);
    }

    protected abstract void checkExistException(String uuid);

    protected abstract void checkNotExistException(String uuid);

    protected abstract Resume getResume(String uuid);

    protected abstract void deleteResume(String uuid);

    protected abstract void saveResume(Resume resume);

    protected abstract void updateResume(Resume resume);
}
