package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.Resume;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<P> implements Storage {
    private final static Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());
    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract void saveResume(Resume resume, P key);

    protected abstract void updateResume(Resume resume, P key);

    protected abstract Resume getResume(P key);

    protected abstract void deleteResume(P key);

    protected abstract P getKey(String uuid);

    protected abstract boolean checkKey(P key);

    protected abstract List<Resume> getList();

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("getAllSorted");
        List<Resume> result = getList();
        result.sort(RESUME_COMPARATOR);
        return result;
    }

    @Override
    public void save(Resume resume) {
        LOGGER.info("Save " + resume.getUuid());
        P key = getKeyIfNotExist(resume.getUuid());
        saveResume(resume, key);
    }

    @Override
    public void update(Resume resume) {
        LOGGER.info("Update " + resume.getUuid());
        P key = getKeyIfExist(resume.getUuid());
        updateResume(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("Get " + uuid);
        P key = getKeyIfExist(uuid);
        return getResume(key);
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("Delete " + uuid);
        P key = getKeyIfExist(uuid);
        deleteResume(key);
    }

    private P getKeyIfNotExist(String uuid) {
        P key = getKey(uuid);
        if (checkKey(key)) {
            LOGGER.warning("Error! Resume " + uuid + " is already in the storage!");
            throw new ExistException(uuid);
        }
        return key;
    }

    private P getKeyIfExist(String uuid) {
        P key = getKey(uuid);
        if (!checkKey(key)) {
            LOGGER.warning("Error! Resume " + uuid + " is not exist!");
            throw new NotExistException(uuid);
        }
        return key;
    }
}
