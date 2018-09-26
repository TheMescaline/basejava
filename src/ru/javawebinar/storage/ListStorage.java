package ru.javawebinar.storage;

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
    protected void deleteResume(Object pointer) {
        int index = (int) pointer;
        storage.remove(index);
    }

    @Override
    protected Object getPointerIfNotExist(String uuid) {
        Resume resume = new Resume(uuid);
        if (storage.contains(resume)) {
            ExistException(uuid);
        }
        return -1;
    }

    @Override
    protected Object getPointerIfExist(String uuid) {
        Resume resume = new Resume(uuid);
        if (!storage.contains(resume)) {
            NotExistException(uuid);
        }
        return storage.indexOf(resume);
    }
}
