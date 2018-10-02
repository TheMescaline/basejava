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
    protected List<Resume> createList() {
        return new ArrayList<>(storage);
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
    protected Integer getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean indexChecker(Object index) {
        return (int) index >= 0;
    }
}
