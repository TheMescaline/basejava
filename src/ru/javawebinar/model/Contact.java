package ru.javawebinar.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Contact implements Serializable {
    private String info;
    private String url;

    public Contact() {
    }

    public Contact(String info) {
        Objects.requireNonNull(info, "info must not be null");
        this.info = info;
    }

    public Contact(String info, String url) {
        this(info);
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!info.equals(contact.info)) return false;
        return url != null ? url.equals(contact.url) : contact.url == null;
    }

    @Override
    public int hashCode() {
        int result = info.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return url != null?  info + " (" + url + ")" : info;
    }
}
