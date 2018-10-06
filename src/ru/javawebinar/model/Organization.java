package ru.javawebinar.model;

import java.util.Objects;

public class Organization {
    private final String name;
    private String url;
    private final String dates;
    private final String position;
    private String info;

    public Organization(String name, String dates, String position) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(dates);
        Objects.requireNonNull(position);
        this.name = name;
        this.dates = dates;
        this.position = position;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public String getDates() {
        return dates;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!name.equals(that.name)) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (!dates.equals(that.dates)) return false;
        if (!position.equals(that.position)) return false;
        return info != null ? info.equals(that.info) : that.info == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + dates.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(url != null ? name + " url: " + url : name).append(System.lineSeparator());
        sb.append("\t").append(dates).append("\t").append(position).append(System.lineSeparator());
        if (info != null) sb.append(info).append(System.lineSeparator());
        return sb.toString();
    }
}
