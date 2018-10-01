package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>(storage.values());
        result.sort(RESUME_COMPARATOR);
        return result;
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
        String key = resume.getUuid();
        storage.put(key, resume);
    }

    @Override
    protected Resume getResume(Object pointer) {
        return (Resume) pointer;
    }

    @Override
    protected void deleteResume(Object pointer) {
        Resume resume = (Resume) pointer;
        storage.remove(resume.getUuid());
    }

    @Override
    protected Resume getIndex(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean indexChecker(Object index) {
        return storage.containsValue(index);
    }
}
