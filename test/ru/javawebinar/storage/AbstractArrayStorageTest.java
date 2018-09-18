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
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume testResume = new Resume("test");
        storage.save(testResume);
        Assert.assertEquals(testResume, storage.get(testResume.getUuid()));
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        try {
            for (int i = 3; i < 100; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

    @Test(expected = ExistException.class)
    public void saveExistException() {
        storage.save(new Resume(UUID_2));
    }

    @Test
    public void update() {
        Resume testResume = new Resume(UUID_2);
        storage.update(testResume);
        Assert.assertEquals(testResume, storage.get(UUID_2));
    }

    @Test(expected = NotExistException.class)
    public void updateNotExistException() {
        storage.update(new Resume("test"));
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        try {
            storage.get(UUID_2);
            Assert.fail();
        } catch (StorageException e) {
            Assert.assertEquals(UUID_2, e.getUuid());
        }
    }

    @Test(expected = NotExistException.class)
    public void deleteNotExistException() {
        storage.delete("test");
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Resume testResume = new Resume("test");
        storage.save(testResume);
        Assert.assertEquals(testResume, storage.get("test"));
    }

    @Test
    public void getAll() {
        Assert.assertEquals(3, storage.getAll().length);
    }
}