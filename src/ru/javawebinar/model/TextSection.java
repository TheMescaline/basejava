package ru.javawebinar.model;

import java.util.Objects;

public class TextSection extends Section {
    private static final long serialVersiodUID = 1L;

    private String text;

    public TextSection() {
    }

    public TextSection(String text) {
        setText(text);
    }

    public void setText(String text) {
        Objects.requireNonNull(text, "text must not be null");
        this.text = text;
    }

    public String getData() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return text + System.lineSeparator();
    }
}
