package ru.javawebinar.storage.serializer;

import ru.javawebinar.model.Resume;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializerStrategy {
    void writeResume(Resume resume, OutputStream outputStream) throws IOException;

    Resume readResume(InputStream inputStream) throws IOException;
}
