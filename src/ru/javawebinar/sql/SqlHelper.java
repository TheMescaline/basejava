package ru.javawebinar.sql;

import ru.javawebinar.exception.ExistException;
import ru.javawebinar.exception.StorageException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public interface SqlQueryExecutor<T> {
        T executeQuery(PreparedStatement preparedStatement) throws SQLException;
    }

    public <T> T runSqlQueryAndReturn(String query, SqlQueryExecutor<T> sqlQueryExecutor) {
        try(Connection connection = connectionFactory.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return sqlQueryExecutor.executeQuery(preparedStatement);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistException(extractUuidFromSQLException(e));
            } else {
                throw new StorageException(e.getMessage(), e);
            }
        }
    }

    private String extractUuidFromSQLException(SQLException e) {
        Matcher matcher = Pattern.compile(".*[=(](.+?)[)].*").matcher(e.getMessage());
        matcher.find();
        return matcher.group(1).trim();
    }
}
