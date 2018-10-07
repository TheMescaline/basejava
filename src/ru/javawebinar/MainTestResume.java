package ru.javawebinar;

import ru.javawebinar.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainTestResume {


    public static void main(String[] args) {
        Resume testResume = new Resume("test", "Test test");

        Contact address = new Contact("Moscow, Russia");
        Contact telephone = new Contact("+7-912-345-67-89");
        Contact email = new Contact("test@email.com", "mailto:test@email.com");
        Contact skype = new Contact("test.test", "skype:test.test");
        Contact git = new Contact("TestAccount", "http://github.com/TestAccount");

        testResume.setContact(ContactType.CELL, telephone);
        testResume.setContact(ContactType.ADDRESS, address);
        testResume.setContact(ContactType.EMAIL, email);
        testResume.setContact(ContactType.SKYPE, skype);
        testResume.setContact(ContactType.GITHUB, git);

        TextSection objective = new TextSection("My position.");
        TextSection personal = new TextSection("My personal");

        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("First achievement");
        achievementsList.add("Second achievement");
        ListSection achievements = new ListSection(achievementsList);

        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("First qualification");
        qualificationsList.add("Second qualification");
        ListSection qualifications = new ListSection(qualificationsList);

        Organization firstOrganization = new Organization(new Contact("Red square", "http://square.com"), LocalDate.of(2009, 1 ,1),LocalDate.of(2009, 2, 1),"Seller", "Was a seller in a store.");
        Organization secondOrganization = new Organization(new Contact("Black Sails"), LocalDate.of(2009, 2, 1), LocalDate.of(2009, 6, 1), "Worker", "Was a worker");

        List<Organization> organizationsList = new ArrayList<>();
        organizationsList.add(firstOrganization);
        organizationsList.add(secondOrganization);
        OrganizationsSection experience = new OrganizationsSection(organizationsList);

        Organization firstUniversity = new Organization(new Contact("MAI"), LocalDate.of(2012, 9, 1), LocalDate.of(2013, 3, 1),"Master degree");
        Organization secondSchool = new Organization(new Contact("School #2"), LocalDate.of(1996, 9, 1), LocalDate.of(2006, 6, 1), "Schoolboy");

        List<Organization> educationList = new ArrayList<>();
        educationList.add(firstUniversity);
        educationList.add(secondSchool);
        OrganizationsSection education = new OrganizationsSection(educationList);

        testResume.setSection(SectionType.PERSONAL, personal);
        testResume.setSection(SectionType.OBJECTIVE, objective);
        testResume.setSection(SectionType.ACHIEVEMENT, achievements);
        testResume.setSection(SectionType.QUALIFICATIONS, qualifications);
        testResume.setSection(SectionType.EXPERIENCE, experience);
        testResume.setSection(SectionType.EDUCATION, education);

        System.out.println(testResume);
    }


}
