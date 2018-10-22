package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage {
    private final IOStrategy strategy;

    public FileStorage(File directory, IOStrategy strategy) {
        super(directory);
        this.strategy = strategy;
    }

    @Override
    protected void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        strategy.writeResume(resume, outputStream);
    }

    @Override
    protected Resume readResume(InputStream inputStream) throws IOException {
        return strategy.readResume(inputStream);
    }
}
