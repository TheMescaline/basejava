package ru.javawebinar.model;

import java.util.Objects;

public class Position {
    private final String dates;
    private final String position;
    private String info;

    public Position(String dates, String position) {
        Objects.requireNonNull(dates, "Dates must not be null");
        Objects.requireNonNull(position, "Position must not be null");
        this.dates = dates;
        this.position = position;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

        Position position1 = (Position) o;

        if (!dates.equals(position1.dates)) return false;
        if (!position.equals(position1.position)) return false;
        return info != null ? info.equals(position1.info) : position1.info == null;
    }

    @Override
    public int hashCode() {
        int result = dates.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dates).append("\t").append(position);
        if (info != null) {
            sb.append(System.lineSeparator()).append("\t\t").append(info);
        }
        return sb.toString();
    }
}
