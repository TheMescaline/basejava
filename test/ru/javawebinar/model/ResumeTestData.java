package ru.javawebinar.model;

import ru.javawebinar.util.DateUtil;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData extends Resume{
    public ResumeTestData(String uuid, String name) {
        super(uuid, name);
       
        this.setContact(ContactType.CELL, new Contact("+7-912-345-67-89"));
        this.setContact(ContactType.ADDRESS, new Contact("Moscow, Russia"));
        this.setContact(ContactType.EMAIL, new Contact("test@email.com", "mailto:test@email.com"));
        this.setContact(ContactType.SKYPE, new Contact("test.test", "skype:test.test"));
        this.setContact(ContactType.GITHUB, new Contact("TestAccount", "http://github.com/TestAccount"));

        ListSection achievements = new ListSection(new ArrayList<>(Arrays.asList("First achievement", "Second achievement")));
        ListSection qualifications = new ListSection(new ArrayList<>(Arrays.asList("First qualification", "Second qualification")));

        Organization firstOrganization = new Organization(new Contact("Red square", "http://square.com"), DateUtil.of(2009, Month.APRIL), DateUtil.of(2009, Month.MAY),"Seller", "Was a seller in a store.");
        Organization secondOrganization = new Organization(new Contact("Black Sails"), DateUtil.of(2009, Month.FEBRUARY), DateUtil.of(2009, Month.JUNE), "Worker", "Was a worker");

        List<Organization> organizationsList = new ArrayList<>();
        organizationsList.add(firstOrganization);
        organizationsList.add(secondOrganization);
        OrganizationsSection experience = new OrganizationsSection(organizationsList);

        Organization firstUniversity = new Organization(new Contact("MAI"), DateUtil.of(2012, Month.SEPTEMBER), DateUtil.of(2013, Month.JUNE),"Master degree");
        Organization secondSchool = new Organization(new Contact("School #2"), DateUtil.of(1996, Month.SEPTEMBER), DateUtil.of(2006, Month.JUNE), "Schoolboy");

        List<Organization> educationList = new ArrayList<>();
        educationList.add(firstUniversity);
        educationList.add(secondSchool);
        OrganizationsSection education = new OrganizationsSection(educationList);

        this.setSection(SectionType.PERSONAL, new TextSection("My personal"));
        this.setSection(SectionType.OBJECTIVE, new TextSection("My position."));
        this.setSection(SectionType.ACHIEVEMENT, achievements);
        this.setSection(SectionType.QUALIFICATIONS, qualifications);
        this.setSection(SectionType.EXPERIENCE, experience);
        this.setSection(SectionType.EDUCATION, education);
    }
}