package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.util.SqlHelper;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.runSqlQueryAndReturn("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>();
        sqlHelper.runSqlQueryAndReturn("SELECT * FROM resume ORDER BY full_name, uuid", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new Resume(resultSet.getString(1).trim(), resultSet.getString(2)));
            }
            return null;
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
        try {
            sqlHelper.runSqlQueryAndReturn("INSERT INTO resume(uuid, full_name) VALUES (?, ?)", preparedStatement -> {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
                return null;
            });
        } catch (StorageException e) {
            throw new ExistException(e.getUuid());
        }
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.runSqlQueryAndReturn("UPDATE resume SET full_name = ? WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            executeAndCheckIfNotExist(resume.getUuid(), preparedStatement);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.runSqlQueryAndReturn("SELECT * FROM resume WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.runSqlQueryAndReturn("DELETE FROM resume WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            executeAndCheckIfNotExist(uuid, preparedStatement);
            return null;
        });
    }

    private void executeAndCheckIfNotExist(String uuid, PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.executeUpdate() != 1) {
            throw new NotExistException(uuid);
        }
    }
}
