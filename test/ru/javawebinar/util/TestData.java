package ru.javawebinar.util;

import ru.javawebinar.model.Resume;

public class TestData {
    private static final String UUID_1 = "1d65731a-1ec4-4588-a607-fb681235934b";
    public static final String UUID_2 = "f5cee95c-d5b6-4d59-8d52-dbc6a6df6754";
    private static final String UUID_3 = "93279d20-f51a-41e9-a434-45bbf42a2a21";
    public static final String NOT_EXISTING_RESUME_UUID = "test";
    public static final Resume NOT_EXISTING_RESUME = ResumeDataFiller.fillResumeWithData(new Resume(NOT_EXISTING_RESUME_UUID, "not existed"), " xxx");
    public static final Resume RESUME_1 = ResumeDataFiller.fillResumeWithData(new Resume(UUID_1, "Alex first"), " #1");
    public static final Resume RESUME_2 = ResumeDataFiller.fillResumeWithData(new Resume(UUID_2, "Billie second"), " #2");
    public static final Resume RESUME_3 = ResumeDataFiller.fillResumeWithData(new Resume(UUID_3, "Charlie third"), " #3");
}
