package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.serializer.SerializerStrategy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final SerializerStrategy strategy;
    private final File directory;

    public FileStorage(File directory, SerializerStrategy strategy) {
        Objects.requireNonNull(directory, "Directory must mot be null");
        Objects.requireNonNull(strategy, "Strategy must mot be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory.");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable.");
        }
        this.directory = directory;
        this.strategy = strategy;
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Can not create file" + file.getAbsolutePath(), file.getName(), e);
        }
        updateResume(resume, file);
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            writeResume(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error!", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return readResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        file.delete();
    }

    @Override
    protected File getKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean checkKey(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> result = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                result.add(getResume(file));
            }
        } else {
            throw new StorageException("There are no resumes in directory!");
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
        String[] names = directory.list();
        if (names == null) {
            throw new StorageException("There are no resumes in directory!");
        }
        return names.length;
    }

    private void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        strategy.writeResume(resume, outputStream);
    }

    private Resume readResume(InputStream inputStream) throws IOException {
        return strategy.readResume(inputStream);
    }
}
