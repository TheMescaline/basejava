package ru.javawebinar.storage;

import ru.javawebinar.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.getInstance().getDatabaseUrl(),
                Config.getInstance().getDatabaseUser(),
                Config.getInstance().getDatabasePassword()));
    }
}