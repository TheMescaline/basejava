package ru.javawebinar.storage;

import ru.javawebinar.storage.serializer.JsonStreamSerializer;

public class JsonStreamFileStorageTest extends AbstractStorageTest {
    public JsonStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new JsonStreamSerializer()));
    }
}