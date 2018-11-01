package ru.javawebinar.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static final long serialVersiodUID = 1L;

    // Unique identifier
    private String uuid;
    private String fullName;
    private Map<ContactType, Contact> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void setContact(ContactType type, Contact contact) {
        contacts.put(type, contact);
    }

    public Contact getContact(ContactType type) {
        return contacts.get(type);
    }

    public void setSection(SectionType type, Section field) {
        sections.put(type, field);
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, Contact> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFullName()).append("\t").append(getUuid()).append(System.lineSeparator()).append(System.lineSeparator());
        for (ContactType type : ContactType.values()) {
            sb.append(type.getTitle()).append(": ").append(contacts.get(type)).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        for (SectionType type : SectionType.values()) {
            sb.append(type.getTitle()).append(System.lineSeparator()).append(sections.get(type)).append(System.lineSeparator());
        }
        return sb.toString();
    }

//    @Override
//    public String toString() {
//        return this.getUuid() + " " + this.getFullName();
//    }
}
