package ru.javawebinar.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    public String marshal(LocalDate date) {
        return date.toString();
    }

    public LocalDate unmarshal(String date) {
        return LocalDate.parse(date);
    }
}
