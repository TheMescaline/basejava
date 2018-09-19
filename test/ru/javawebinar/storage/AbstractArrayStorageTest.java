package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

public class AbstractArrayStorageTest {
    private Storage storage;
    private int sizeBeforeTest;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume notExistingResume = new Resume("test");
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
        sizeBeforeTest = storage.size();
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(notExistingResume);
        Assert.assertEquals(notExistingResume, storage.get(notExistingResume.getUuid()));
        Assert.assertEquals(sizeBeforeTest + 1, storage.size());
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        try {
            for (int i = sizeBeforeTest; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

    @Test(expected = ExistException.class)
    public void saveExistException() {
        storage.save(resume2);
    }

    @Test
    public void update() {
        storage.update(resume2);
        Assert.assertEquals(resume2, storage.get(UUID_2));
    }

    @Test(expected = NotExistException.class)
    public void updateNotExistException() {
        storage.update(notExistingResume);
    }

    @Test(expected = NotExistException.class)
    public void delete() {
        storage.delete(UUID_2);
        Assert.assertEquals(sizeBeforeTest - 1, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NotExistException.class)
    public void deleteNotExistException() {
        storage.delete(notExistingResume.getUuid());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        storage.save(notExistingResume);
        Assert.assertEquals(notExistingResume, storage.get(notExistingResume.getUuid()));
    }

    @Test(expected = NotExistException.class)
    public void getNotExistException() {
        storage.get(notExistingResume.getUuid());
    }

    @Test
    public void getAll() {
        Resume[] sampleArray = new Resume[]{resume1, resume2, resume3};
        Resume[] testArray = storage.getAll();
        Assert.assertEquals(sampleArray.length, testArray.length);
        for (int i = 0; i < sampleArray.length; i++) {
            Assert.assertEquals(sampleArray[i], testArray[i]);
        }
    }
}