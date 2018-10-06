package ru.javawebinar.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    // Unique identifier
    private final String uuid;
    private final String fullName;
    private Map<String, Contact> contacts = new HashMap<>();
    private Map<SectionType, Field> sections = new HashMap<>();

    public void setContact(String name, Contact contact) {
        contacts.put(name, contact);
    }

    public Contact getContact(String name) {
        return contacts.get(name);
    }

    public void setSection(SectionType type, Field field) {
        sections.put(type, field);
    }

    public Field getSection(SectionType type) {
        return sections.get(type);
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

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void printFullInfo() {
        System.out.println(getFullName());
        System.out.println();
        for (Map.Entry<String, Contact> pair : contacts.entrySet()) {
            System.out.println(pair.getKey() + ": " + pair.getValue());
        }
        System.out.println();
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            System.out.println(sections.get(type));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resume of " + fullName + ". UUID: " + uuid;
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo((o.fullName));
    }
}
