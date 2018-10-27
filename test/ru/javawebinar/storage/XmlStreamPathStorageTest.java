package ru.javawebinar.storage;

import ru.javawebinar.storage.serializer.XmlStreamSerializer;

public class XmlStreamPathStorageTest extends AbstractStorageTest {
    public XmlStreamPathStorageTest() {
        super(new PathStorage(DIR, new XmlStreamSerializer()));
    }
}