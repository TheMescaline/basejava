package ru.javawebinar.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private String info;
    private String url;

    public Link() {
    }

    public Link(String info) {
        Objects.requireNonNull(info, "info must not be null");
        this.info = info;
    }

    public Link(String info, String url) {
        this(info);
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(info, link.info) &&
                Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(info, url);
    }

    @Override
    public String toString() {
        return url != null?  info + " (" + url + ")" : info;
    }
}
