package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must mot be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory.");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable.");
        }
        this.directory = directory;
    }

    protected abstract void writeResume(Resume resume, File file) throws IOException;

    protected abstract Resume readResume(File file) throws IOException;

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            writeResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", resume.getUuid(), e);
        }

    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            writeResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        Resume result;
        try {
            result = readResume(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        return result;
    }

    @Override
    protected void deleteResume(File file) {
        file.delete();
    }

    @Override
    protected File getIndex(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean indexChecker(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> createList() {
        List<Resume> result = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                result.add(getResume(file));
            }
        }
        return result;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @Override
    public int size() {
        int result = -1;
        String[] names = directory.list();
        if (names != null) result = names.length;
        return result;
    }
}
