package ru.javawebinar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPERTIES = new File("config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private File storageDirectory;
    private String databaseUrl;
    private String databaseUser;
    private String databasePassword;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try(InputStream inputStream = new FileInputStream(PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            storageDirectory = new File(properties.getProperty("storage.directory.path"));
            databaseUrl = properties.getProperty("db.url");
            databaseUser = properties.getProperty("db.user");
            databasePassword = properties.getProperty("db.password");

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

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }
}
