package com.codingchili.webshoppe.model;

import java.sql.*;

/**
 * Created by Robin on 2015-09-28.
 *
 * Initializes the connector/driver for the MySQL Database
 * with the use of connection pooling.
 */
class Database {
    private static final String HOST = "jdbc:mysql://localhost:3306/WebShop";
    private static final String USER = "webshopaccount";
    private static final String PASSWORD = "RainbowUnicornSquirrelOfTheMultiverse";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(HOST, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
