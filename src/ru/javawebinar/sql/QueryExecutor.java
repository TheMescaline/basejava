package ru.javawebinar.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface QueryExecutor<T> {
    T executeQuery(PreparedStatement preparedStatement) throws SQLException;
}
