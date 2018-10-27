package ru.javawebinar.model;

import ru.javawebinar.util.LocalDateAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.util.DateUtil.NOW;
import static ru.javawebinar.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersiodUID = 1L;

    private Contact contact;
    private List<Position> positions;

    public Organization() {
    }

    public Organization(Contact contact, List<Position> positions) {
        Objects.requireNonNull(contact, "contact must not be null");
        Objects.requireNonNull(positions, "list of positions must not be null");
        this.contact = contact;
        this.positions = positions;
    }

    public Organization(String info, String url, Position... positions) {
        this(new Contact(info, url), Arrays.asList(positions));
    }

    public String getUrl() {
        return contact.getUrl();
    }

    public String getName() {
        return contact.getInfo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!contact.equals(that.contact)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = contact.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(contact.getInfo());
        String url = contact.getUrl();
        if (url != null) sb.append("\turl: ").append(url);
        for (Position position : positions) {
            sb.append(position);
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable{
        private static final long serialVersiodUID = 1L;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String position;
        private String info;

        public Position() {
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String position, String info) {
            this(of(startYear, startMonth), of(endYear, endMonth), position, info);
        }

        public Position(int startYear, Month startMonth, String position, String info) {
            this(of(startYear, startMonth), NOW, position, info);
        }

        public Position(LocalDate startDate, LocalDate endDate, String position, String info) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(position, "position must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.position = position;
            this.info = info;
        }

        public LocalDate getStartDates() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getPosition() {
            return position;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position1 = (Position) o;

            if (!startDate.equals(position1.startDate)) return false;
            if (!endDate.equals(position1.endDate)) return false;
            if (!position.equals(position1.position)) return false;
            return info != null ? info.equals(position1.info) : position1.info == null;
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + position.hashCode();
            result = 31 * result + (info != null ? info.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            int startMonth = startDate.getMonthValue();
            int endMonth = endDate.getMonthValue();
            sb.append(System.lineSeparator()).
                    append("\t").
                    append(startMonth < 10 ? "0" + startMonth : startMonth).
                    append("/").
                    append(startDate.getYear()).
                    append("-").
                    append(endMonth < 10 ? "0" + endMonth : endMonth).
                    append("/").
                    append(endDate.getYear()).
                    append("\t").
                    append(position).
                    append(System.lineSeparator());
            if (info != null) sb.append("\t\t").append(info);
            return sb.toString();
        }
    }
}
