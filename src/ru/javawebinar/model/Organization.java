package ru.javawebinar.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final Contact contact;
    private final List<Position> positions;

    public Organization(Contact contact, List<Position> positions) {
        Objects.requireNonNull(contact, "contact must not be null");
        Objects.requireNonNull(positions, "list of positions must not be null");
        this.contact = contact;
        this.positions = positions;
    }

    public String getUrl() {
        return contact.getUrl();
    }

    public String getName() {
        return contact.getInfo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!contact.equals(that.contact)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = contact.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(contact.getInfo());
        String url = contact.getUrl();
        if (url != null) sb.append("\turl: ").append(url);
        for (Position position : positions) {
            sb.append(position);
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
