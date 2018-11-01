package ru.javawebinar.storage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        ListStorageTest.class,
        ObjectStreamFileStorageTest.class,
        ObjectStreamPathStorageTest.class,
        XmlStreamFileStorageTest.class,
        XmlStreamPathStorageTest.class,
        JsonStreamFileStorageTest.class,
        DataStreamPathStorageTest.class})
public class RunAllTests {
}
