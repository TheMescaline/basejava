package ru.javawebinar.model;

import java.util.List;

public class OrganizationsList extends Field<List<Organization>> {
    private final List<Organization> organizationList;

    public OrganizationsList(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    public List<Organization> getData() {
        return organizationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationsList that = (OrganizationsList) o;

        return organizationList != null ? organizationList.equals(that.organizationList) : that.organizationList == null;
    }

    @Override
    public int hashCode() {
        return organizationList != null ? organizationList.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization organization : organizationList) {
            sb.append(organization);
        }
        return sb.toString();
    }
}
