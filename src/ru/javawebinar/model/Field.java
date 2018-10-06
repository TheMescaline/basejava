package ru.javawebinar.model;

public abstract class Field<T> {
    private SectionType type;

    protected abstract T getData();

    public SectionType getType() {
        return type;
    }
}
