package ru.javawebinar;

import ru.javawebinar.model.*;
import ru.javawebinar.util.DateUtil;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTestResume {
    public static void main(String[] args) {
        Resume testResume = new Resume("test", "Test test");

        testResume.setContact(ContactType.CELL, new Contact("+7-912-345-67-89"));
        testResume.setContact(ContactType.ADDRESS, new Contact("Moscow, Russia"));
        testResume.setContact(ContactType.EMAIL, new Contact("test@email.com", "mailto:test@email.com"));
        testResume.setContact(ContactType.SKYPE, new Contact("test.test", "skype:test.test"));
        testResume.setContact(ContactType.GITHUB, new Contact("TestAccount", "http://github.com/TestAccount"));

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

        testResume.setSection(SectionType.PERSONAL, new TextSection("My personal"));
        testResume.setSection(SectionType.OBJECTIVE, new TextSection("My position."));
        testResume.setSection(SectionType.ACHIEVEMENT, achievements);
        testResume.setSection(SectionType.QUALIFICATIONS, qualifications);
        testResume.setSection(SectionType.EXPERIENCE, experience);
        testResume.setSection(SectionType.EDUCATION, education);

        System.out.println(testResume);
    }
}
