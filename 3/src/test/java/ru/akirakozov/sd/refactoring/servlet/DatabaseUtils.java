package ru.akirakozov.sd.refactoring.servlet;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
    private static final String DATABASE_NAME = "jdbc:sqlite:test.db";

    static void clear() throws SQLException {
        try (Statement statement = DriverManager.getConnection(DATABASE_NAME).createStatement()) {
            String sqlQuery = "DELETE FROM PRODUCT";
            statement.executeUpdate(sqlQuery);
        }
    }

    static void add(Integer numberOfProducts) throws SQLException {
        for (int i = 1; i <= numberOfProducts; i++) {
            add("product" + i, i);
        }
    }

    private static void add(String name, int price) throws SQLException {
        try (Statement statement = DriverManager.getConnection(DATABASE_NAME).createStatement()) {
            String sqlQuery = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
            statement.executeUpdate(sqlQuery);
        }
    }
}
