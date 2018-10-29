package ru.javawebinar.storage;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.util.ResumeDataFiller;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AbstractStorageTest {
    protected final static String DIR = "D:/Coding/project/basejava/storage";
    protected final static File STORAGE_DIR = new File(DIR);

    protected Storage storage;
    protected int sizeBeforeTest;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String NOT_EXISTING_RESUME_UUID = "test";
    private static final Resume NOT_EXISTING_RESUME = new Resume(NOT_EXISTING_RESUME_UUID, "not existed");
    private static final Resume RESUME_1 = ResumeDataFiller.fillResumeWithData(new Resume(UUID_1, "Alex first"), " #1");
    private static final Resume RESUME_2 = ResumeDataFiller.fillResumeWithData(new Resume(UUID_2, "Billie second"), " #2");
    private static final Resume RESUME_3 = ResumeDataFiller.fillResumeWithData(new Resume(UUID_3, "Charlie third"), " #3");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_3);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        sizeBeforeTest = storage.size();
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() {
        storage.save(NOT_EXISTING_RESUME);
        assertGet(NOT_EXISTING_RESUME, NOT_EXISTING_RESUME_UUID);
        assertSize(sizeBeforeTest + 1);
    }

    @Test(expected = ExistException.class)
    public void saveExistException() {
        storage.save(RESUME_2);
    }

    @Test
    public void update() {
        Resume sample = new Resume(UUID_2, "New name");
        storage.update(sample);
        assertGet(sample, UUID_2);
    }

    @Test(expected = NotExistException.class)
    public void updateNotExistException() {
        storage.update(NOT_EXISTING_RESUME);
    }

    @Test(expected = NotExistException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(sizeBeforeTest - 1);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistException.class)
    public void deleteNotExistException() {
        storage.delete(NOT_EXISTING_RESUME_UUID);
    }

    @Test
    public void size() {
        assertSize(sizeBeforeTest);
    }

    @Test
    public void get() {
        assertGet((Resume) RESUME_2, UUID_2);
    }

    @Test(expected = NotExistException.class)
    public void getNotExistException() {
        storage.get(NOT_EXISTING_RESUME_UUID);
    }

    @Test
    public void getAllSorted() {
        List<Resume> sample = new ArrayList<>(Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
        List<Resume> testArray = storage.getAllSorted();
        assertEquals(sample, testArray);
    }

    private void assertGet(Resume resume, String uuid) {
        assertEquals(resume, storage.get(uuid));
    }

    private void assertSize(int sizeBeforeTest) {
        assertEquals(sizeBeforeTest, storage.size());
    }
}