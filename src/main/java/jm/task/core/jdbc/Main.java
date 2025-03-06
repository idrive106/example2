package jm.task.core.jdbc;

import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("insert into user (name, lastName, age) values ('Вадим', 'Богатов', 34)");
            statement.execute("insert into user (name, lastName, age) values ('Анна', 'Толстова', 3)");
            statement.execute("insert into user (name, lastName, age) values ('Андрей', 'Разгуляев', 9)");
            statement.execute("insert into user (name, lastName, age) values ('Марина', 'Мандаринова', 36)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}