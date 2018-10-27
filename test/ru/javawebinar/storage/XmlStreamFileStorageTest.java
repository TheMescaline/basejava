package ru.javawebinar.storage;

import ru.javawebinar.storage.serializer.XmlStreamSerializer;

public class XmlStreamFileStorageTest extends AbstractStorageTest {
    public XmlStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}