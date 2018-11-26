package ru.javawebinar.util;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.sql.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public interface SqlExecutor {
        void execute(PreparedStatement preparedStatement) throws SQLException;
    }

    public interface SqlQueryExecutor<T> {
        T executeQuery(PreparedStatement preparedStatement) throws SQLException;
    }

    public void runSqlQuery(String query, SqlExecutor sqlExecutor) {
        try(Connection connection = connectionFactory.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            sqlExecutor.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e.getMessage(), e);
        }
    }

    public <T> T runSqlQueryAndReturn(String query, SqlQueryExecutor<T> sqlQueryExecutor) {
        try(Connection connection = connectionFactory.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return sqlQueryExecutor.executeQuery(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e.getMessage(), e);
        }
    }
}
