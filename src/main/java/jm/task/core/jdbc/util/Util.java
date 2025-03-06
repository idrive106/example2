package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/learn";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0504";

    public static Connection getConnection() {
        try {
            System.out.println("Подключение к базе установлено");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения");
            throw new RuntimeException(e);
        }
    }
}