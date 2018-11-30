package ru.javawebinar.storage.serializer;

import ru.javawebinar.model.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements SerializerStrategy {
    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            writeCollection(dataOutputStream, resume.getContacts().entrySet(), contactsEntry -> {
                dataOutputStream.writeUTF(contactsEntry.getKey().name());
                dataOutputStream.writeUTF(contactsEntry.getValue());
            });
            writeCollection(dataOutputStream, resume.getSections().entrySet(), sectionsEntry -> {
                SectionType sectionType = sectionsEntry.getKey();
                dataOutputStream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dataOutputStream.writeUTF(((TextSection) sectionsEntry.getValue()).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dataOutputStream, ((ListSection) sectionsEntry.getValue()).getListOfLines(), dataOutputStream::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeCollection(dataOutputStream, ((OrganizationsSection) sectionsEntry.getValue()).getOrganizationsList(), organization -> {
                            dataOutputStream.writeUTF(organization.getName());
                            dataOutputStream.writeUTF(organization.getUrl());
                            writeCollection(dataOutputStream, organization.getPositions(), position -> {
                                dataOutputStream.writeUTF(position.getStartDate().toString());
                                dataOutputStream.writeUTF(position.getEndDate().toString());
                                dataOutputStream.writeUTF(position.getPosition());
                                dataOutputStream.writeUTF(position.getInfo());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume readResume(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            Resume result = new Resume(dataInputStream.readUTF(), dataInputStream.readUTF());
            readMap(dataInputStream, () -> result.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF()));
            readMap(dataInputStream, () -> {
                SectionType type = SectionType.valueOf(dataInputStream.readUTF());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        result.setSection(type, new TextSection(dataInputStream.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        result.setSection(type, new ListSection(readList(dataInputStream, dataInputStream::readUTF)));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        result.setSection(type, new OrganizationsSection(readList(dataInputStream, () -> new Organization(
                                new Link(dataInputStream.readUTF(), dataInputStream.readUTF()),
                                readList(dataInputStream, () -> new Organization.Position(
                                        LocalDate.parse(dataInputStream.readUTF()),
                                        LocalDate.parse(dataInputStream.readUTF()),
                                        dataInputStream.readUTF(),
                                        dataInputStream.readUTF()))))));
                        break;
                }
            });
            return result;
        }
    }

    private interface DataWriter<T> {
        void write(T data) throws IOException;
    }

    private interface DataReader<T> {
        T read() throws IOException;
    }

    private interface MapReader {
        void readAndInitializeMap() throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dataOutputStream, Collection<T> collection, DataWriter<T> dataWriter) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T data : collection) {
            dataWriter.write(data);
        }
    }

    private <T> List<T> readList(DataInputStream dataInputStream, DataReader<T> dataReader) throws IOException {
        int listSize = dataInputStream.readInt();
        List<T> list = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            list.add(dataReader.read());
        }
        return list;
    }

    private void readMap(DataInputStream dataInputStream, MapReader mapReader) throws IOException {
        int mapSize = dataInputStream.readInt();
        for (int i = 0; i < mapSize; i++) {
            mapReader.readAndInitializeMap();
        }
    }
}
