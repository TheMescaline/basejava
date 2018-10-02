package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.Resume;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<P> implements Storage {
    private final static Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void saveResume(Resume resume, P pointer);

    protected abstract void updateResume(Resume resume, P pointer);

    protected abstract Resume getResume(P pointer);

    protected abstract void deleteResume(P pointer);

    protected abstract P getIndex(String uuid);

    protected abstract boolean indexChecker(P index);

    protected abstract List<Resume> createList();

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("getAllSorted");
        List<Resume> result = createList();
        Collections.sort(result);
        return result;
    }

    @Override
    public void save(Resume resume) {
        LOGGER.info("Save " + resume);
        P pointer = getPointerIfNotExist(resume.getUuid());
        saveResume(resume, pointer);
    }

    @Override
    public void update(Resume resume) {
        LOGGER.info("Update " + resume);
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
        P index = getIndex(uuid);
        if (indexChecker(index)) {
            LOGGER.warning("Error! Resume " + uuid + " is already in the storage!");
            throw new ExistException(uuid);
        }
        return index;
    }

    private P getPointerIfExist(String uuid) {
        P index = getIndex(uuid);
        if (!indexChecker(index)) {
            LOGGER.warning("Error! Resume " + uuid + " is not exist!");
            throw new NotExistException(uuid);
        }
        return index;
    }
}
