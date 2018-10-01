package ru.javawebinar.model;

import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume{
    // Unique identifier
    private final String uuid;
    private final String fullName;

    public Resume(String fullName) {
        this.fullName = fullName;
        this.uuid = UUID.randomUUID().toString();
    }

    public Resume(String fullName, String uuid) {
        this.fullName = fullName;
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;
        if (!this.getFullName().equals(resume.getFullName())) return false;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode() + fullName.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }
}
