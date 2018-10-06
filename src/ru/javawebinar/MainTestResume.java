package ru.javawebinar;

import ru.javawebinar.model.*;
import java.util.ArrayList;
import java.util.List;

public class MainTestResume {


    public static void main(String[] args) {
        Resume testResume = new Resume("test", "Алексей Коровин");

        Contact address = new Contact("Moscow, Russia");
        Contact telephone = new Contact("+7-916-886-91-30");
        Contact vkontakte = new Contact("Профиль Вконтакте");
        vkontakte.setUrl("http://vk.com/ray_zee");

        testResume.setContact("Address", address);
        testResume.setContact("Cell", telephone);
        testResume.setContact("Vkontakte", vkontakte);

        TextField objective = new TextField("My position.");
        TextField personal = new TextField("My personal");

        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("First achievement");
        achievementsList.add("Second achievement");
        ListField achievements = new ListField(achievementsList);

        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("First qualification");
        qualificationsList.add("Second qualification");
        ListField qualifications = new ListField(qualificationsList);

        Position firstJob = new Position("01/2009 - 03/2009","Seller");
        firstJob.setInfo("Was a seller in a store.");
        Position secondJob = new Position("05/2009 - 06/2009", "Worker");
        secondJob.setInfo("Was a worker");
        Position thirdJob = new Position("06/2009 - 09/2009", "Boss");

        Organization firstOrganization = new Organization("Red stone", firstJob);
        Organization secondOrganization = new Organization("Black Sails", secondJob);
        secondOrganization.addPosition(thirdJob);

        List<Organization> organizationsList = new ArrayList<>();
        organizationsList.add(firstOrganization);
        organizationsList.add(secondOrganization);
        OrganizationsList experience = new OrganizationsList(organizationsList);

        Position firstEducation = new Position("09/2012 - 03/2013","Master degree");
        Position secondEducation = new Position("09/2006 - 06/2012", "Student");
        Position thirdEducation = new Position("09/1996 - 06/2006", "Schoolboy");

        Organization firstUniversity = new Organization("MAI", firstEducation);
        firstUniversity.addPosition(secondEducation);
        Organization secondSchool = new Organization("School #2", thirdEducation);

        List<Organization> educationList = new ArrayList<>();
        educationList.add(firstUniversity);
        educationList.add(secondSchool);
        OrganizationsList education = new OrganizationsList(educationList);

        testResume.setSection(SectionType.PERSONAL, personal);
        testResume.setSection(SectionType.OBJECTIVE, objective);
        testResume.setSection(SectionType.ACHIEVEMENT, achievements);
        testResume.setSection(SectionType.QUALIFICATIONS, qualifications);
        testResume.setSection(SectionType.EXPERIENCE, experience);
        testResume.setSection(SectionType.EDUCATION, education);

        testResume.printFullInfo();
    }


}
