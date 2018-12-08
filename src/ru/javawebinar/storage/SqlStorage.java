package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistException;
import ru.javawebinar.model.*;
import ru.javawebinar.sql.SqlHelper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SqlStorage implements Storage {
    private static final String CONTACT = "contact";
    private static final String SECTION = "section";
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
            fillResumeWithContactsGetAllSorted(resumes, connection, new ContactFiller());
            fillResumeWithSectionsGetAllSorted(resumes, connection, new SectionFiller());
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
            fillResumeWithContacts(connection, resume, new ContactFiller());
            fillResumeWithSections(connection, resume, new SectionFiller());
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

    private interface ResumeFiller {
        void fill(ResultSet resultSet, Resume resume) throws SQLException;
    }

    private class SectionFiller implements ResumeFiller {
        @Override
        public void fill(ResultSet resultSet, Resume resume) throws SQLException {
            SectionType type = SectionType.valueOf(resultSet.getString(TYPE));
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.setSection(type, new TextSection(resultSet.getString(VALUE)));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> list = Stream.of(resultSet.getString(VALUE).split(System.lineSeparator()))
                            .collect(Collectors.toList());
                    resume.setSection(type, new ListSection(list));
                    break;
            }
        }
    }

    private class ContactFiller implements ResumeFiller {
        @Override
        public void fill(ResultSet resultSet, Resume resume) throws SQLException {
            resume.addContact(ContactType.valueOf(resultSet.getString(TYPE)), resultSet.getString(VALUE));
        }

    }

    private void clearContactTable(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void clearSectionTable(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void fillResumeWithContacts(Connection connection, Resume resume, ResumeFiller filler) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume LEFT JOIN contact ON resume.uuid = contact.resume_uuid WHERE uuid = ?")) {
            prepareAndFill(preparedStatement, resume, filler);
        }
    }

    private void fillResumeWithSections(Connection connection, Resume resume, ResumeFiller filler) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume LEFT JOIN section ON resume.uuid = section.resume_uuid WHERE uuid = ?")) {
            prepareAndFill(preparedStatement, resume, filler);
        }
    }

    private void prepareAndFill(PreparedStatement preparedStatement, Resume resume, ResumeFiller filler) throws SQLException {
        preparedStatement.setString(1, resume.getUuid());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String type = resultSet.getString(TYPE);
            if (type != null) {
                filler.fill(resultSet, resume);
            }
        }
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
                setQueryValues(resume.getUuid(), section.getKey().name(), section.getValue().toString(), preparedStatement);
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

    private void executeAndCheckIfNotExist(String uuid, PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.executeUpdate() != 1) {
            throw new NotExistException(uuid);
        }
    }

    private void fillResumeWithContactsGetAllSorted(Map<String, Resume> resumes, Connection connection, ResumeFiller resumeFiller) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resumeFiller.fill(resultSet, resumes.get(resultSet.getString("resume_uuid")));
            }
        }
    }

    private void fillResumeWithSectionsGetAllSorted(Map<String, Resume> resumes, Connection connection, ResumeFiller resumeFiller) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resumeFiller.fill(resultSet, resumes.get(resultSet.getString("resume_uuid")));
            }
        }
    }
}
