package ru.javawebinar.util;

import ru.javawebinar.model.*;
import java.time.Month;

public class ResumeDataFiller {
    public static Resume fillResumeWithData(Resume resume, String differenceSuffix) {
        resume.setContact(ContactType.CELL, new Contact("+7-912-345-67-89" + differenceSuffix));
        resume.setContact(ContactType.ADDRESS, new Contact("Moscow, Russia" + differenceSuffix));
        resume.setContact(ContactType.EMAIL, new Contact("test@email.com" + differenceSuffix, "mailto:test@email.com"));
        resume.setContact(ContactType.SKYPE, new Contact("test.test" + differenceSuffix, "skype:test.test"));
        resume.setContact(ContactType.GITHUB, new Contact("TestAccount" + differenceSuffix, "http://github.com/TestAccount"));

        resume.setSection(SectionType.PERSONAL, new TextSection("My personal" + differenceSuffix));
        resume.setSection(SectionType.OBJECTIVE, new TextSection("My position." + differenceSuffix));
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("First achievement" + differenceSuffix, "Second achievement" + differenceSuffix));
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection("First qualification" + differenceSuffix, "Second qualification" + differenceSuffix));
        resume.setSection(SectionType.EXPERIENCE, new OrganizationsSection(
                new Organization("Red square" + differenceSuffix, "http://square.com",
                        new Organization.Position(2009, Month.APRIL, 2009, Month.MAY,"Seller" + differenceSuffix, "Was a seller in a store." + differenceSuffix)),
                new Organization("Black Sails" + differenceSuffix, null,
                        new Organization.Position(2009, Month.FEBRUARY,2009, Month.JUNE, "Worker" + differenceSuffix, "Was a worker" + differenceSuffix),
                        new Organization.Position(2009, Month.JUNE, 2009, Month.DECEMBER, "Boss" + differenceSuffix, "Was a Boss" + differenceSuffix))));
        resume.setSection(SectionType.EDUCATION, new OrganizationsSection(
                new Organization("MAI" + differenceSuffix, null,
                        new Organization.Position(2012, Month.SEPTEMBER, 2013, Month.JUNE,"Master degree" + differenceSuffix, null)),
                new Organization("School #2" + differenceSuffix, null,
                        new Organization.Position(1996, Month.SEPTEMBER, 2006, Month.JUNE, "Schoolboy" + differenceSuffix, null))
        ));
        return resume;
    }
}
