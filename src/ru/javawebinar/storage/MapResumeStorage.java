package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void saveResume(Resume resume, Resume key) {
        String tempKey = resume.getUuid();
        storage.put(tempKey, resume);
    }

    @Override
    protected void updateResume(Resume resume, Resume key) {
        String tempKey = resume.getUuid();
        storage.put(tempKey, resume);
    }

    @Override
    protected Resume getResume(Resume key) {
        return key;
    }

    @Override
    protected void deleteResume(Resume key) {
        storage.remove(key.getUuid());
    }

    @Override
    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean checkKey(Resume index) {
        return index != null;
    }
}
