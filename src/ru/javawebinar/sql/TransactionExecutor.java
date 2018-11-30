package ru.javawebinar.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface TransactionExecutor<T> {
    public T execute(Connection connection) throws SQLException;
}
