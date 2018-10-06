package ru.javawebinar.model;

public class Contact {
    private final String info;
    private String url;

    public Contact(String info) {
        this.info = info;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (info != null ? !info.equals(contact.info) : contact.info != null) return false;
        return url != null ? url.equals(contact.url) : contact.url == null;
    }

    @Override
    public int hashCode() {
        int result = info != null ? info.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return url != null?  info + " (" + url + ")" : info;
    }
}
