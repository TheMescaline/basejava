package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.ContactType;
import ru.javawebinar.model.Resume;
import ru.javawebinar.sql.SqlHelper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executeQuery("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>();
        sqlHelper.transactionalExecute(connection -> {
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result.add(new Resume(resultSet.getString(1).trim(), resultSet.getString(2)));
                }
            }
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                for (Resume resume : result) {
                    preparedStatement.setString(1, resume.getUuid());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        resume.addContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                    }
                }
            }
            return null;
        });
        return result;
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery("SELECT COUNT(uuid) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES (?, ?)")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }
            insertContacts(resume, connection);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                executeAndCheckIfNotExist(resume.getUuid(), preparedStatement);
            }
            try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.execute();
            }
            insertContacts(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeQuery(
                "SELECT * FROM resume " +
                        "LEFT JOIN contact ON resume.uuid = contact.resume_uuid " +
                        "WHERE uuid = ?", preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistException(uuid);
                    }
                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                    do {
                        resume.addContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                    } while (resultSet.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeQuery("DELETE FROM resume WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            executeAndCheckIfNotExist(uuid, preparedStatement);
            return null;
        });
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact(resume_uuid, type, value) VALUES (?, ? ,?)")) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, contact.getKey().name());
                preparedStatement.setString(3, contact.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void executeAndCheckIfNotExist(String uuid, PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.executeUpdate() != 1) {
            throw new NotExistException(uuid);
        }
    }
}
