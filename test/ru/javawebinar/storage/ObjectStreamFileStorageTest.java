package ru.javawebinar.storage;

import ru.javawebinar.storage.serializer.ObjectStreamSerializer;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}