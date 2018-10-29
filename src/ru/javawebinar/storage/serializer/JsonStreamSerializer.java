package ru.javawebinar.storage.serializer;

import ru.javawebinar.model.Resume;
import ru.javawebinar.util.JsonParser;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements SerializerStrategy {
    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume readResume(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
