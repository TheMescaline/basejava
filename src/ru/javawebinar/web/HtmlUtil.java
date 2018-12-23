package ru.javawebinar.web;

import ru.javawebinar.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public static String printSection(SectionType type, Section value) {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                return ((TextSection) value).getText();
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return String.join("<br/>", ((ListSection) value).getListOfLines());
            case EXPERIENCE:
            case EDUCATION:
                StringBuilder sb;
                List<String> organizations = new ArrayList<>();
                for (Organization org : ((OrganizationsSection) value).getOrganizationsList()) {
                    sb = new StringBuilder();
                    Link link = org.getLink();
                    sb.append("<strong>");
                    sb.append(link.getUrl().isEmpty() ? link.getInfo() : "<a href=" + link.getUrl() + ">" + link.getInfo() + "</a>");
                    sb.append("</strong><br/>");
                    List<Organization.Position> positions = org.getPositions();
                    for (Organization.Position position : positions) {
                        StringBuilder sbInner = new StringBuilder();
                        LocalDate startDate = position.getStartDate();
                        int startMonth = startDate.getMonthValue();
                        LocalDate endDate = position.getEndDate();
                        int endMonth = endDate.getMonthValue();
                        sbInner.append(startMonth < 10 ? "0" + startMonth : startMonth).
                                append("/").
                                append(startDate.getYear()).
                                append("-").
                                append(endMonth < 10 ? "0" + endMonth : endMonth).
                                append("/").
                                append(endDate.getYear()).
                                append("&nbsp<strong>").
                                append(position.getPosition()).
                                append("</strong><br/>");
                        String info = position.getInfo();
                        if (!info.isEmpty()) sbInner.append(info).append("<br/>");
                        sb.append(sbInner.toString());
                    }
                    organizations.add(sb.toString());
                }
                return String.join("<br/>", organizations);
            default:
                    throw new IllegalStateException();
        }
    }
}
