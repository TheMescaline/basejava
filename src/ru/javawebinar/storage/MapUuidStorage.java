package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> createList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void saveResume(Resume resume, Object pointer) {
        String key = resume.getUuid();
        storage.put(key, resume);
    }

    @Override
    protected void updateResume(Resume resume, Object pointer) {
        String key = String.valueOf(pointer);
        storage.put(key, resume);
    }

    @Override
    protected Resume getResume(Object pointer) {
        String key = String.valueOf(pointer);
        return storage.get(key);
    }

    @Override
    protected void deleteResume(Object pointer) {
        String key = String.valueOf(pointer);
        storage.remove(key);
    }

    @Override
    protected String getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean indexChecker(Object index) {
        return storage.containsKey(index.toString());
    }
}
