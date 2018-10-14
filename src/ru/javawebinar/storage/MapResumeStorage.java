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
    protected void saveResume(Resume resume, Resume pointer) {
        String key = resume.getUuid();
        storage.put(key, resume);
    }

    @Override
    protected void updateResume(Resume resume, Resume pointer) {
        String key = resume.getUuid();
        storage.put(key, resume);
    }

    @Override
    protected Resume getResume(Resume pointer) {
        return pointer;
    }

    @Override
    protected void deleteResume(Resume pointer) {
        storage.remove(pointer.getUuid());
    }

    @Override
    protected Resume getPointer(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean pointerChecker(Resume index) {
        return index != null;
    }
}
