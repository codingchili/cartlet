package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.Properties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by Robin on 2015-09-28.
 * <p>
 * Initializes the connector/driver for the MySQL Database
 * with the use of connection pooling.
 */
class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
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

    public static <E> E prepared(String query, QueryImplementation<E> consumer) throws SQLException {
        long start = System.currentTimeMillis();
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                E result = consumer.accept(connection, preparedStatement);
                logger.info("[" + (System.currentTimeMillis() - start) + "ms.] " + query);
                return result;
            }
        }
    }

    @FunctionalInterface
    public interface QueryImplementation<E> {
        E accept(Connection connection, PreparedStatement statement) throws RuntimeException, SQLException;
    }
}