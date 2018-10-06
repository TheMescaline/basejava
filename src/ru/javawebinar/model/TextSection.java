package ru.javawebinar.model;

import java.util.Objects;

public class TextSection extends Section<String> {
    private String text;

    public TextSection(String text) {
        Objects.requireNonNull(text, "text must not be null");
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getData() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return text + System.lineSeparator();
    }
}
