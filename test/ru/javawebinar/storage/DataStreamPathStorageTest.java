package ru.javawebinar.storage;

import ru.javawebinar.storage.serializer.DataStreamSerializer;

public class DataStreamPathStorageTest extends AbstractStorageTest {
    public DataStreamPathStorageTest() {
        super(new PathStorage(DIR, new DataStreamSerializer()));
    }
}