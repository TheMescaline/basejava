package ru.javawebinar.storage;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.javawebinar.Config;
import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.ContactType;
import ru.javawebinar.model.Resume;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.util.TestData.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AbstractStorageTest {
    final static String DIR = Config.getInstance().getStorageDirectoryPath();
    final static File STORAGE_DIR = Config.getInstance().getStorageDirectory();

    protected Storage storage;
    int sizeBeforeTest;

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
        sample.addContact(ContactType.EMAIL, "sample@email");
        sample.addContact(ContactType.GITHUB, "sample/github");
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
        assertGet(RESUME_2, UUID_2);
    }

    @Test(expected = NotExistException.class)
    public void getNotExistException() {
        storage.get(NOT_EXISTING_RESUME_UUID);
    }

    @Test
    public void getAllSorted() {
        List<Resume> sample = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
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