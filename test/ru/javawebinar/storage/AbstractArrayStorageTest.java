package ru.javawebinar.storage;

import org.junit.Test;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import static org.junit.Assert.fail;

public class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        try {
            for (int i = sizeBeforeTest; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("test" + i));
            }
        } catch (StorageException e) {
            fail("Unpredicted exception! Wrong storage overflow logic.");
        }
        storage.save(new Resume("test exception"));
    }
}