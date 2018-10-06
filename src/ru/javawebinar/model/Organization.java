package ru.javawebinar.model;

import java.util.Objects;

public class Organization {
    private final Contact contact;
    private final String dates;
    private final String position;
    private String info;

    public Organization(Contact contact, String dates, String position) {
        Objects.requireNonNull(contact, "contact must not be null");
        Objects.requireNonNull(dates, "dates must not be null");
        Objects.requireNonNull(position, "position must not be null");
        this.contact = contact;
        this.dates = dates;
        this.position = position;
    }

    public Organization(Contact contact, String dates, String position, String info) {
        this(contact, dates, position);
        this.info = info;
    }

    public String getUrl() {
        return contact.getUrl();
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return contact.getInfo();
    }

    public String getDates() {
        return dates;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!contact.equals(that.contact)) return false;
        if (!dates.equals(that.dates)) return false;
        if (!position.equals(that.position)) return false;
        return info != null ? info.equals(that.info) : that.info == null;
    }

    @Override
    public int hashCode() {
        int result = contact.hashCode();
        result = 31 * result + dates.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(contact.getInfo());
        String url = contact.getUrl();
        if (url != null) sb.append("\turl: ").append(url);
        sb.append(System.lineSeparator()).append("\t").append(dates).append("\t").append(position).append(System.lineSeparator());
        if (info != null) sb.append(info).append(System.lineSeparator());
        return sb.toString();
    }
}
