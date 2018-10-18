package ru.javawebinar;

import ru.javawebinar.model.*;
import java.time.Month;

public class MainTestResume {
    public static void main(String[] args) {
        Resume testResume = new Resume("test", "Test test");

        testResume.setContact(ContactType.CELL, new Contact("+7-912-345-67-89"));
        testResume.setContact(ContactType.ADDRESS, new Contact("Moscow, Russia"));
        testResume.setContact(ContactType.EMAIL, new Contact("test@email.com", "mailto:test@email.com"));
        testResume.setContact(ContactType.SKYPE, new Contact("test.test", "skype:test.test"));
        testResume.setContact(ContactType.GITHUB, new Contact("TestAccount", "http://github.com/TestAccount"));

        testResume.setSection(SectionType.PERSONAL, new TextSection("My personal"));
        testResume.setSection(SectionType.OBJECTIVE, new TextSection("My position."));
        testResume.setSection(SectionType.ACHIEVEMENT, new ListSection("First achievement", "Second achievement"));
        testResume.setSection(SectionType.QUALIFICATIONS, new ListSection("First qualification", "Second qualification"));
        testResume.setSection(SectionType.EXPERIENCE, new OrganizationsSection(
                new Organization("Red square", "http://square.com",
                        new Organization.Position(2009, Month.APRIL, 2009, Month.MAY,"Seller", "Was a seller in a store.")),
                new Organization("Black Sails", null,
                        new Organization.Position(2009, Month.FEBRUARY,2009, Month.JUNE, "Worker", "Was a worker"),
                        new Organization.Position(2009, Month.JUNE, 2009, Month.DECEMBER, "Boss", "Was a Boss"))));
        testResume.setSection(SectionType.EDUCATION, new OrganizationsSection(
                new Organization("MAI", null,
                        new Organization.Position(2012, Month.SEPTEMBER, 2013, Month.JUNE,"Master degree", null)),
                new Organization("School #2", null,
                        new Organization.Position(1996, Month.SEPTEMBER, 2006, Month.JUNE, "Schoolboy", null))
        ));

        System.out.println(testResume);
    }
}