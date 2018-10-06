package ru.javawebinar.model;

import java.util.List;

public class ListField extends Field<List<String>> {
    private List<String> list;

    public ListField(List<String> list) {
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

        ListField listField = (ListField) o;

        return list != null ? list.equals(listField.list) : listField.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
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
