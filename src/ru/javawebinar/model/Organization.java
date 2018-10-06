package ru.javawebinar.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private final String name;
    private String url;
    private final List<Position> datesAndInfoLines = new ArrayList<>();

    public Organization(String name, Position position) {
        this.name = name;
        datesAndInfoLines.add(position);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public String getName() {
        return name;
    }

    public void addPosition(Position position) {
        datesAndInfoLines.add((position));
    }

    public List<Position> getInfo() {
        return datesAndInfoLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return datesAndInfoLines.equals(that.datesAndInfoLines);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + datesAndInfoLines.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(url != null ? name + " url: " + url : name).append(System.lineSeparator());
        for (Position pos : datesAndInfoLines) {
            sb.append("\t").append(pos).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
