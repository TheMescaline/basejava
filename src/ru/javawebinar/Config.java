package ru.javawebinar;

import ru.javawebinar.storage.SqlStorage;
import ru.javawebinar.storage.Storage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPERTIES = new File("config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private final File storageDirectory;
    private final Storage storage;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try(InputStream inputStream = new FileInputStream(PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            storageDirectory = new File(properties.getProperty("storage.directory.path"));
            storage = new SqlStorage(properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file: " + PROPERTIES.getAbsolutePath());
        }
    }

    public File getStorageDirectory() {
        return storageDirectory;
    }

    public String getStorageDirectoryPath() {
        return storageDirectory.getAbsolutePath();
    }

    public Storage getStorage() {
        return storage;
    }
}
