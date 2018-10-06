package ru.javawebinar.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section<List<String>> {
    private final List<String> list;

    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    @Override
    public List<String> getData() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String line : list) {
            sb.append("- ").append(line).append(System.lineSeparator());
        }
        return sb.toString();
    }
}