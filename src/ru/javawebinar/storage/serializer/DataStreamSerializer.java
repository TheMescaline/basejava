package ru.javawebinar.storage.serializer;

import ru.javawebinar.model.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializerStrategy {

    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try(DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            Map<ContactType, Contact> contacts = resume.getContacts();
            dataOutputStream.writeInt(contacts.size());
            for (Map.Entry<ContactType, Contact> pair : contacts.entrySet()) {
                dataOutputStream.writeUTF(pair.getKey().name());
                writeData(dataOutputStream, pair.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dataOutputStream.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> pair : sections.entrySet()) {
                SectionType sectionType = pair.getKey();
                dataOutputStream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dataOutputStream.writeUTF(((TextSection) pair.getValue()).getData());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dataOutputStream, ((ListSection) pair.getValue()).getData());
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeCollection(dataOutputStream, ((OrganizationsSection) pair.getValue()).getData());
                        break;
                }
            }
        }
    }

    private <T> void writeCollection(DataOutputStream dataOutputStream, List<T> list) throws IOException {
        dataOutputStream.writeInt(list.size());
        for (T object : list) {
            writeData(dataOutputStream, object);
        }
    }

    private <T> void writeData(DataOutputStream dataOutputStream, T object) throws IOException {
        if (object instanceof Organization) {
            writeData(dataOutputStream, ((Organization) object).getContact());
            writeCollection(dataOutputStream, ((Organization) object).getPositions());
        } else if (object instanceof Organization.Position) {
            dataOutputStream.writeUTF(((Organization.Position) object).getStartDate().toString());
            dataOutputStream.writeUTF(((Organization.Position) object).getEndDate().toString());
            dataOutputStream.writeUTF(((Organization.Position) object).getPosition());
            String info = ((Organization.Position) object).getInfo();
            if (info != null) {
                dataOutputStream.writeBoolean(true);
                dataOutputStream.writeUTF(info);
            } else {
                dataOutputStream.writeBoolean(false);
            }
        } else if (object instanceof Contact) {
            dataOutputStream.writeUTF(((Contact) object).getInfo());
            String url = ((Contact) object).getUrl();
            if (url != null) {
                dataOutputStream.writeBoolean(true);
                dataOutputStream.writeUTF(url);
            } else {
                dataOutputStream.writeBoolean(false);
            }
        } else if (object instanceof String) {
            dataOutputStream.writeUTF((String) object);
        } else {
            throw new ClassCastException("Wrong method call!");
        }
    }

    @Override
    public Resume readResume(InputStream inputStream) throws IOException {
        try(DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            Resume result = new Resume(dataInputStream.readUTF(), dataInputStream.readUTF());
            int contactsSize = dataInputStream.readInt();
            for (int i = 0; i < contactsSize; i++) {
                ContactType type = ContactType.valueOf(dataInputStream.readUTF());
                Contact contact = new Contact(dataInputStream.readUTF());
                if (dataInputStream.readBoolean()) {
                    contact.setUrl(dataInputStream.readUTF());
                }
                result.setContact(type, contact);
            }
            int sectionsSize = dataInputStream.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType type = SectionType.valueOf(dataInputStream.readUTF());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        result.setSection(type, new TextSection(dataInputStream.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int listSectionSize = dataInputStream.readInt();
                        List<String> listSection = new ArrayList<>();
                        for (int j = 0; j < listSectionSize; j++) {
                            listSection.add(dataInputStream.readUTF());
                        }
                        result.setSection(type, new ListSection(listSection));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        int organizationSectionSize = dataInputStream.readInt();
                        List<Organization> organizationList = new ArrayList<>();
                        for (int j = 0; j < organizationSectionSize; j++) {
                            Contact contact = new Contact(dataInputStream.readUTF());
                            if (dataInputStream.readBoolean()) {
                                contact.setUrl(dataInputStream.readUTF());
                            }
                            int positionsListSize = dataInputStream.readInt();
                            List<Organization.Position> positionList = new ArrayList<>();
                            for (int k = 0; k < positionsListSize; k++) {
                                Organization.Position position = new Organization.Position(
                                        LocalDate.parse(dataInputStream.readUTF()),
                                        LocalDate.parse(dataInputStream.readUTF()),
                                        dataInputStream.readUTF(),
                                        null);
                                if (dataInputStream.readBoolean()) {
                                    position.setInfo(dataInputStream.readUTF());
                                }
                                positionList.add(position);
                            }
                            organizationList.add(new Organization(contact, positionList));
                        }
                        result.setSection(type, new OrganizationsSection(organizationList));
                        break;
                }
            }
            return result;
        }
    }
}
