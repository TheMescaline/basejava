package ru.javawebinar.web;

import ru.javawebinar.model.ContactType;

public class HtmlUtil {
    public static String printContact(ContactType type, String value) {
        switch (type) {
            case CELL:
            case ADDRESS:
                return value;
            case EMAIL:
                return "<a href=\"mailto:" + value + "\">" + value + "</a>";
            case SKYPE:
                return "<a href=\"skype:" + value + "\">" + value + "</a>";
            case GITHUB:
                return "<a href=\"http://github.com/" + value + "\">" + value + "</a>";
            case LINKEDIN:
            case STACKOVERFLOW:
                return "<a href=\"" + value + "\">" + value + "</a>";
        }
        return "";
    }
}
