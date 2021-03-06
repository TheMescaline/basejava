package ru.javawebinar.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    public static final Section EMPTY = new ListSection("");
    private static final long serialVersionUID = 1L;

    private List<String> listOfLines;

    public ListSection() {
    }

    public ListSection(List<String> listOfLines) {
        Objects.requireNonNull(listOfLines, "listOfLines must not be null");
        this.listOfLines = listOfLines;
    }

    public ListSection(String... lines) {
        this(Arrays.asList(lines));
    }

    public List<String> getListOfLines() {
        return listOfLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(listOfLines, that.listOfLines);
    }

    @Override
    public int hashCode() {

        return Objects.hash(listOfLines);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), listOfLines);
    }
}
