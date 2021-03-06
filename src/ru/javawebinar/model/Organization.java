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
    private static final long serialVersionUID = 1L;

    public static final Organization EMPTY = new Organization("", "", Position.EMPTY);

    private Link link;
    private List<Position> positions;

    public Organization() {
    }

    public Organization(Link link, List<Position> positions) {
        Objects.requireNonNull(link, "link must not be null");
        Objects.requireNonNull(positions, "list of positions must not be null");
        this.link = link;
        this.positions = positions;
    }

    public Organization(String info, String url, Position... positions) {
        this(new Link(info, url), Arrays.asList(positions));
    }

    public String getUrl() {
        return link.getUrl();
    }

    public String getName() {
        return link.getInfo();
    }

    public Link getLink() {
        return link;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(link, that.link) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(link, positions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(link.getInfo());
        String url = link.getUrl();
        if (url != null) sb.append("\turl: ").append(url);
        for (Position position : positions) {
            sb.append(position);
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable{
        public static final Position EMPTY = new Position();
        private static final long serialVersionUID = 1L;

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
            if (info != null) {
                this.info = info;
            } else {
                this.info = "";
            }
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getPosition() {
            return position;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position1 = (Position) o;
            return Objects.equals(startDate, position1.startDate) &&
                    Objects.equals(endDate, position1.endDate) &&
                    Objects.equals(position, position1.position) &&
                    Objects.equals(info, position1.info);
        }

        @Override
        public int hashCode() {

            return Objects.hash(startDate, endDate, position, info);
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
