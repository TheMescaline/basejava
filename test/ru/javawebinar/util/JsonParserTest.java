package ru.javawebinar.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.model.Resume;
import ru.javawebinar.model.Section;
import ru.javawebinar.model.TextSection;

import static ru.javawebinar.util.TestData.RESUME_1;

public class JsonParserTest {


    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_1, Resume.class);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() {
        Section section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}