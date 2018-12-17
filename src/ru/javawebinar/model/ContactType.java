package ru.javawebinar.model;

public enum ContactType {
    CELL("Тел."),
    ADDRESS("Адрес"),
    EMAIL("E-mail"),
    SKYPE("Skype"),
    GITHUB("Профиль Github"),
    LINKEDIN("Профиль LinkedIn"),
    STACKOVERFLOW("Профиль StackOverflow");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
