package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
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
    protected Object getPointerIfNotExist(String uuid) {
        if (storage.containsKey(uuid)) {
            ExistException(uuid);
        }
        return -1;
    }

    @Override
    protected Object getPointerIfExist(String uuid) {
        if (!storage.containsKey(uuid)) {
            NotExistException(uuid);
        }
        return uuid;
    }
}
