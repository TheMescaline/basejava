package ru.javawebinar.storage.serializer;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectStreamSerializer implements SerializerStrategy {
    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
        }
    }

    @Override
    public Resume readResume(InputStream inputStream) throws IOException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Resume read error!", null, e);
        }
    }
}
