package ru.javawebinar.model;

import java.time.LocalDate;
import java.util.Objects;

public class Position {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String position;
    private String info;

    public Position(LocalDate startDate, LocalDate endDate, String position) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(position, "position must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
    }

    public Position(LocalDate startDate, LocalDate endDate, String position, String info) {
        this(startDate, endDate, position);
        this.info = info;
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

        Position position1 = (Position) o;

        if (!startDate.equals(position1.startDate)) return false;
        if (!endDate.equals(position1.endDate)) return false;
        if (!position.equals(position1.position)) return false;
        return info != null ? info.equals(position1.info) : position1.info == null;
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
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
        if (info != null) sb.append("\t\t").append(info);
        return sb.toString();
    }
}
