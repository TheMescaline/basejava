package ru.javawebinar.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationsSection extends Section {
    private static final long serialVersiodUID = 1L;

    private List<Organization> organizationList;

    public OrganizationsSection() {
    }

    public OrganizationsSection(List<Organization> organizationList) {
        Objects.requireNonNull(organizationList, "list must not be null");
        this.organizationList = organizationList;
    }

    public OrganizationsSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public List<Organization> getData() {
        return organizationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationsSection that = (OrganizationsSection) o;

        return organizationList.equals(that.organizationList);
    }

    @Override
    public int hashCode() {
        return organizationList.hashCode();
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
