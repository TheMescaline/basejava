package ru.javawebinar.model;

import java.time.Month;

public class ResumeTestData extends Resume{
    public ResumeTestData(String uuid, String name) {
        super(uuid, name);
       
        this.setContact(ContactType.CELL, new Contact("+7-912-345-67-89"));
        this.setContact(ContactType.ADDRESS, new Contact("Moscow, Russia"));
        this.setContact(ContactType.EMAIL, new Contact("test@email.com", "mailto:test@email.com"));
        this.setContact(ContactType.SKYPE, new Contact("test.test", "skype:test.test"));
        this.setContact(ContactType.GITHUB, new Contact("TestAccount", "http://github.com/TestAccount"));

        this.setSection(SectionType.PERSONAL, new TextSection("My personal"));
        this.setSection(SectionType.OBJECTIVE, new TextSection("My position."));
        this.setSection(SectionType.ACHIEVEMENT, new ListSection("First achievement", "Second achievement"));
        this.setSection(SectionType.QUALIFICATIONS, new ListSection("First qualification", "Second qualification"));
        this.setSection(SectionType.EXPERIENCE, new OrganizationsSection(
                new Organization("Red square", "http://square.com",
                        new Organization.Position(2009, Month.APRIL, 2009, Month.MAY,"Seller", "Was a seller in a store.")),
                new Organization("Black Sails", null,
                        new Organization.Position(2009, Month.FEBRUARY,2009, Month.JUNE, "Worker", "Was a worker"),
                        new Organization.Position(2009, Month.JUNE, 2009, Month.DECEMBER, "Boss", "Was a Boss"))));
        this.setSection(SectionType.EDUCATION, new OrganizationsSection(
                new Organization("MAI", null,
                        new Organization.Position(2012, Month.SEPTEMBER, 2013, Month.JUNE,"Master degree", null)),
                new Organization("School #2", null,
                        new Organization.Position(1996, Month.SEPTEMBER, 2006, Month.JUNE, "Schoolboy", null))
        ));
    }
}
