package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.sql.ConnectionFactory;
import ru.javawebinar.util.SqlHelper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.runSqlQuery("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>();
        sqlHelper.runSqlQuery("SELECT * FROM resume ORDER BY full_name, uuid", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new Resume(resultSet.getString(1).trim(), resultSet.getString(2)));
            }
        });
        return result;
    }

    @Override
    public int size() {
        return sqlHelper.runSqlQueryAndReturn("SELECT COUNT(uuid) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.runSqlQuery("INSERT INTO resume(uuid, full_name) VALUES (?, ?)", preparedStatement -> {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, resume.getFullName());
            executeAndCheckIfExist(resume.getUuid());
            preparedStatement.execute();
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.runSqlQuery("UPDATE resume SET full_name = ? WHERE uuid = ? RETURNING full_name", preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            executeAndCheckIfNotExist(resume.getUuid(), preparedStatement);
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.runSqlQueryAndReturn("SELECT * FROM resume WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = executeAndCheckIfNotExist(uuid, preparedStatement);
            return new Resume(uuid, resultSet.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.runSqlQuery("DELETE FROM resume WHERE uuid = ? RETURNING *", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            executeAndCheckIfNotExist(uuid, preparedStatement);
        });
    }

    private ResultSet executeAndCheckIfNotExist(String uuid, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            throw new NotExistException(uuid);
        }
        return resultSet;
    }

    private void executeAndCheckIfExist(String uuid) {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                throw new ExistException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e.getMessage(), e);
        }
    }
}
