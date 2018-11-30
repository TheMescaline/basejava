package ru.javawebinar;

import ru.javawebinar.model.*;
import java.time.Month;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume testResume = new Resume("test", "Test test");

        testResume.addContact(ContactType.CELL, "+7-912-345-67-89");
        testResume.addContact(ContactType.ADDRESS, "Moscow, Russia");
        testResume.addContact(ContactType.EMAIL, "test@email.com");
        testResume.addContact(ContactType.SKYPE, "test.test");
        testResume.addContact(ContactType.GITHUB, "http://github.com/TestAccount");

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

    }
}