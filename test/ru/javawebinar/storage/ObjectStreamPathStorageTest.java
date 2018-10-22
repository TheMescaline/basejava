package ru.javawebinar.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(DIR, new IOByObjectStream()));
    }
}