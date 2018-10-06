package ru.javawebinar;

import ru.javawebinar.model.*;
import java.util.ArrayList;
import java.util.List;

public class MainTestResume {


    public static void main(String[] args) {
        Resume testResume = new Resume("test", "Test test");

        Contact address = new Contact("Moscow, Russia");
        Contact telephone = new Contact("+7-912-345-67-89");
        Contact email = new Contact("test@email.com");
        email.setUrl("mailto:test@email.com");
        Contact skype = new Contact("test.test");
        skype.setUrl("skype:test.test");
        Contact git = new Contact("TestAccount");
        git.setUrl("http://github.com/TestAccount");

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

        Organization firstOrganization = new Organization("Red stone", "01/2009 - 03/2009","Seller");
        firstOrganization.setInfo("Was a seller in a store.");
        Organization secondOrganization = new Organization("Black Sails", "05/2009 - 06/2009", "Worker");
        secondOrganization.setInfo("Was a worker");

        List<Organization> organizationsList = new ArrayList<>();
        organizationsList.add(firstOrganization);
        organizationsList.add(secondOrganization);
        OrganizationsSection experience = new OrganizationsSection(organizationsList);

        Organization firstUniversity = new Organization("MAI", "09/2012 - 03/2013","Master degree");
        Organization secondSchool = new Organization("School #2", "09/1996 - 06/2006", "Schoolboy");

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

        testResume.printFullInfo();
    }


}
