package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.serializer.SerializerStrategy;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final SerializerStrategy strategy;
    private final Path directory;

    public PathStorage(String dir, SerializerStrategy strategy) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "Directory must mot be null");
        Objects.requireNonNull(strategy, "Strategy must mot be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory) || !Files.isReadable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable/readable");
        }
        this.directory = directory;
        this.strategy = strategy;
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        updateResume(resume, path);
    }

    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            writeResume(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error!", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", getFileName(path), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Can not delete path " + path.toString(), getFileName(path), e);
        }
    }

    @Override
    protected Path getKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean checkKey(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> getList() {
        return getFilesList().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    private void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        strategy.writeResume(resume, outputStream);
    }

    private Resume readResume(InputStream inputStream) throws IOException {
        return strategy.readResume(inputStream);
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Path delete error!", e);
        }
    }
}
