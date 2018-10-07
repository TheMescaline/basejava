package ru.javawebinar.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private final Contact contact;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String position;
    private String info;

    public Organization(Contact contact, LocalDate startDate, LocalDate endDate, String position) {
        Objects.requireNonNull(contact, "contact must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(position, "position must not be null");
        this.contact = contact;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
    }

    public Organization(Contact contact, LocalDate startDate, LocalDate endDate, String position, String info) {
        this(contact, startDate, endDate, position);
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

    public LocalDate getStartDates() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
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
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!position.equals(that.position)) return false;
        return info != null ? info.equals(that.info) : that.info == null;
    }

    @Override
    public int hashCode() {
        int result = contact.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
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
        int startMonth = startDate.getMonthValue();
        int endMonth = endDate.getMonthValue();
        sb.append(System.lineSeparator()).
                append("\t").
                append(startMonth < 10 ? "0" + startMonth : startMonth).
                append("/").
                append(startDate.getYear()).
                append("-").
                append(endMonth < 10 ? "0" + endMonth : endMonth).
                append("/").
                append(endDate.getYear()).
                append("\t").
                append(position).
                append(System.lineSeparator());
        if (info != null) sb.append(info).append(System.lineSeparator());
        return sb.toString();
    }
}
