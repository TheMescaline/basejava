package ru.javawebinar.model;

public enum ContactType {
    CELL("Тел."),
    ADDRESS("Адрес"),
    EMAIL("E-mail"),
    SKYPE("Skype"),
    GITHUB("Github");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
