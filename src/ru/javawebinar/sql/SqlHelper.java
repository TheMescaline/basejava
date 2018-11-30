package ru.javawebinar.sql;

import ru.javawebinar.exception.StorageException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeQuery(String query, QueryExecutor<T> queryExecutor) {
        try(Connection connection = connectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return queryExecutor.executeQuery(preparedStatement);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw SqlToStorageExceptionWrapper.wrap(e);
            } else {
                throw new StorageException(e.getMessage(), e);
            }
        }
    }

    public <T> T transactionalExecute(TransactionExecutor<T> transactionExecutor) {
        try(Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = transactionExecutor.execute(connection);
                connection.commit();
                return result;
            }catch (SQLException e) {
                connection.rollback();
                throw SqlToStorageExceptionWrapper.wrap(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e.getMessage(), e);
        }
    }
}
