package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.ContactType;
import ru.javawebinar.model.Resume;
import ru.javawebinar.model.Section;
import ru.javawebinar.model.SectionType;
import ru.javawebinar.sql.SqlHelper;
import ru.javawebinar.util.JsonParser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private static final String TYPE = "type";
    private static final String VALUE = "value";

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Error while loading database driver", e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executeQuery("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        return sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resumes.put(resultSet.getString(1), new Resume(resultSet.getString(1), resultSet.getString(2)));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    fillContacts(resultSet, resumes.get(resultSet.getString("resume_uuid")));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    fillSections(resultSet, resumes.get(resultSet.getString("resume_uuid")));
                }
            }
            return new ArrayList<>(resumes.values());
        });
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
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                executeAndCheckIfNotExist(resume.getUuid(), preparedStatement);
            }
            clearContactTable(resume, connection);
            clearSectionTable(resume, connection);
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, resume.getUuid());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String type = resultSet.getString(TYPE);
                    if (type != null) {
                        fillContacts(resultSet, resume);
                    }
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, resume.getUuid());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String type = resultSet.getString(TYPE);
                    if (type != null) {
                        fillSections(resultSet, resume);
                    }
                }
            }
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

    private void fillSections(ResultSet resultSet, Resume resume) throws SQLException {
        SectionType type = SectionType.valueOf(resultSet.getString(TYPE));
        resume.setSection(type, JsonParser.read(resultSet.getString(VALUE), Section.class));
    }

    private void fillContacts(ResultSet resultSet, Resume resume) throws SQLException {
        ContactType type = ContactType.valueOf(resultSet.getString(TYPE));
        resume.addContact(type, resultSet.getString(VALUE));
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact(resume_uuid, type, value) VALUES (?, ? ,?)")) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                setQueryValues(resume.getUuid(), contact.getKey().name(), contact.getValue(), preparedStatement);
            }
            preparedStatement.executeBatch();
        }
    }

    private void insertSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO section(resume_uuid, type, value) VALUES (?, ? ,?)")) {
            for (Map.Entry<SectionType, Section> section : resume.getSections().entrySet()) {
                setQueryValues(resume.getUuid(), section.getKey().name(), JsonParser.write(section.getValue(), Section.class), preparedStatement);
            }
            preparedStatement.executeBatch();
        }
    }

    private void setQueryValues(String firstValue, String secondValue, String thirdValue, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, firstValue);
        preparedStatement.setString(2, secondValue);
        preparedStatement.setString(3, thirdValue);
        preparedStatement.addBatch();
    }

    private void clearContactTable(Resume resume, Connection connection) throws SQLException {
        clearAttributes(resume, connection, "DELETE FROM contact WHERE resume_uuid = ?");
    }

    private void clearSectionTable(Resume resume, Connection connection) throws SQLException {
        clearAttributes(resume, connection, "DELETE FROM section WHERE resume_uuid = ?");
    }

    private void clearAttributes(Resume resume, Connection connection, String s) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(s)) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void executeAndCheckIfNotExist(String uuid, PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.executeUpdate() != 1) {
            throw new NotExistException(uuid);
        }
    }
}