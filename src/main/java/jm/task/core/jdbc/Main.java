package jm.task.core.jdbc;

import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Connection connection = Util.getConnection();

        Util.closeConnection(connection);
    }
}