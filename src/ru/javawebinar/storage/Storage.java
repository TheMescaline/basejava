package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public interface Storage {
    void clear();

    List<Resume> getAllSorted();

    int size();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);
}
