package ru.javawebinar.storage.serializer;

import ru.javawebinar.model.*;
import ru.javawebinar.util.XmlParser;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements SerializerStrategy {
    private final XmlParser parser = new XmlParser(
            Resume.class,
            Contact.class,
            TextSection.class,
            ListSection.class,
            OrganizationsSection.class,
            Organization.class,
            Organization.Position.class);

    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            parser.marshal(resume, writer);
        }
    }

    @Override
    public Resume readResume(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return parser.unmarshal(reader);
        }
    }
}
