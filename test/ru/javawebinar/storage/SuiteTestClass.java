package ru.javawebinar.storage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ArrayStorageTest.class, SortedArrayStorageTest.class, MapStorageTest.class, ListStorageTest.class})
public class SuiteTestClass {
}
