package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.Resume;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<P> implements Storage {
    private final static Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());
    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract void saveResume(Resume resume, P pointer);

    protected abstract void updateResume(Resume resume, P pointer);

    protected abstract Resume getResume(P pointer);

    protected abstract void deleteResume(P pointer);

    protected abstract P getPointer(String uuid);

    protected abstract boolean pointerChecker(P pointer);

    protected abstract List<Resume> getList();

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("getAllSorted");
        List<Resume> result = getList();
        Collections.sort(result, RESUME_COMPARATOR);
        return result;
    }

    @Override
    public void save(Resume resume) {
        LOGGER.info("Save " + resume.getUuid());
        P pointer = getPointerIfNotExist(resume.getUuid());
        saveResume(resume, pointer);
    }

    @Override
    public void update(Resume resume) {
        LOGGER.info("Update " + resume.getUuid());
        P pointer = getPointerIfExist(resume.getUuid());
        updateResume(resume, pointer);
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("Get " + uuid);
        P pointer = getPointerIfExist(uuid);
        return getResume(pointer);
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("Delete " + uuid);
        P pointer = getPointerIfExist(uuid);
        deleteResume(pointer);
    }

    private P getPointerIfNotExist(String uuid) {
        P pointer = getPointer(uuid);
        if (pointerChecker(pointer)) {
            LOGGER.warning("Error! Resume " + uuid + " is already in the storage!");
            throw new ExistException(uuid);
        }
        return pointer;
    }

    private P getPointerIfExist(String uuid) {
        P pointer = getPointer(uuid);
        if (!pointerChecker(pointer)) {
            LOGGER.warning("Error! Resume " + uuid + " is not exist!");
            throw new NotExistException(uuid);
        }
        return pointer;
    }
}
