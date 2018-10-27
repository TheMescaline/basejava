package ru.javawebinar.storage;

import ru.javawebinar.storage.serializer.ObjectStreamSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(DIR, new ObjectStreamSerializer()));
    }
}