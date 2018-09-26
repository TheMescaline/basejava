package ru.javawebinar.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.Resume;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AbstractStorageTest {
    protected Storage storage;
    protected int sizeBeforeTest;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String NOT_EXISTING_RESUME_UUID = "test";
    private static final Resume NOT_EXISTING_RESUME = new Resume(NOT_EXISTING_RESUME_UUID);
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
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
        storage.update(RESUME_2);
        assertGet(RESUME_2, UUID_2);
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
    public void getAll() {
        Resume[] sampleArray = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Resume[] testArray = storage.getAll();
        Arrays.sort(testArray);
        assertArrayEquals(sampleArray, testArray);
    }

    private void assertGet(Resume resume, String uuid) {
        assertEquals(resume, storage.get(uuid));
    }

    private void assertSize(int sizeBeforeTest) {
        assertEquals(sizeBeforeTest, storage.size());
    }
}