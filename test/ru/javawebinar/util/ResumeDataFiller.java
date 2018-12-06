package ru.javawebinar.util;

import ru.javawebinar.model.ContactType;
import ru.javawebinar.model.ListSection;
import ru.javawebinar.model.Resume;
import ru.javawebinar.model.SectionType;
import ru.javawebinar.model.TextSection;

public class ResumeDataFiller {
    public static Resume fillResumeWithData(Resume resume, String differenceSuffix) {
        resume.addContact(ContactType.CELL, "+7-912-345-67-89" + differenceSuffix);
        resume.addContact(ContactType.ADDRESS, "Moscow, Russia" + differenceSuffix);
        resume.addContact(ContactType.EMAIL, "test@email.com" + differenceSuffix);
        resume.addContact(ContactType.SKYPE, "test.test" + differenceSuffix);
//        resume.addContact(ContactType.GITHUB, "http://github.com/TestAccount" + differenceSuffix);

        resume.setSection(SectionType.PERSONAL, new TextSection("My personal" + differenceSuffix));
        resume.setSection(SectionType.OBJECTIVE, new TextSection("My position." + differenceSuffix));
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("First achievement" + differenceSuffix, "Second achievement" + differenceSuffix));
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection("First qualification" + differenceSuffix, "Second qualification" + differenceSuffix));
        /*resume.setSection(SectionType.EXPERIENCE, new OrganizationsSection(
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
        ));*/
        return resume;
    }
}
