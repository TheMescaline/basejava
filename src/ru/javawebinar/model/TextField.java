package ru.javawebinar.model;

public class TextField extends Field<String> {
    private String text;

    public TextField(String text) {
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

        TextField textField = (TextField) o;

        return text != null ? text.equals(textField.text) : textField.text == null;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return text + System.lineSeparator();
    }
}
