package ru.javawebinar.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.StorageException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlToStorageExceptionWrapper {
    private SqlToStorageExceptionWrapper() {
        throw new IllegalStateException("Utility class");
    }

    public static StorageException wrap(SQLException e) {
        if (e instanceof PSQLException && e.getSQLState().equals("23505")) {
            return new ExistException(extractUuidFromSQLException(e));
        }
        return new StorageException(e.getMessage(), e);
    }

    private static String extractUuidFromSQLException(SQLException e) {
        Matcher matcher = Pattern.compile(".*[=(](.+?)[)].*").matcher(e.getMessage());
        matcher.find();
        return matcher.group(1).trim();
    }
}
