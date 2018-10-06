package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.Properties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

/**
 * Created by Robin on 2015-09-28.
 *
 * Initializes the connector/driver for the MySQL Database
 * with the use of connection pooling.
 */
class Database {

    private static HikariDataSource ds;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Properties properties = Properties.get();
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getJdbcUrl());
            config.setUsername(properties.getDatabaseUser());
            config.setPassword(properties.getDatabasePass());
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("useServerPrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            ds = new HikariDataSource(config);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
