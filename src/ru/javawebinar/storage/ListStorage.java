package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.Resume;
import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void saveResume(Resume resume, Object pointer) {
        storage.add(resume);
    }

    @Override
    protected void updateResume(Resume resume, Object pointer) {
        int index = (int) pointer;
        storage.set(index, resume);
    }

    @Override
    protected Resume getResume(Object pointer) {
        int index = (int) pointer;
        return storage.get(index);
    }

    @Override
    protected void setUpStorage(Object pointer) {
        int index = (int) pointer;
        storage.remove(index);
    }

    @Override
    protected void clearStorage() {
        //nothing to do here
    }

    @Override
    protected boolean isStorageNotFull(Resume resume) {
        //nothing to do here
        return true;
    }

    @Override
    protected Object getPointerIfNotExist(Resume resume) {
        if (storage.contains(resume)) {
            throw new ExistException(resume.getUuid());
        }
        return -1;
    }

    @Override
    protected Object getPointerIfExist(Resume resume) {
        if (!storage.contains(resume)) {
            throw new NotExistException(resume.getUuid());
        }
        return storage.indexOf(resume);
    }
}
