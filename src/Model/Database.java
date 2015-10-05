package Model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

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
    private static Database database;
    private MysqlDataSource source;


    private Database() {
        source = new MysqlDataSource();
        source.setUser(USER);
        source.setPassword(PASSWORD);
        source.setUrl(HOST);
    }

    public static Connection getConnection() throws SQLException {
        if (database == null) {
                threadSafeInit();
        }
            return database.source.getConnection();
    }

    private synchronized static void threadSafeInit() {
        if (database == null) {
            database = new Database();
        }
    }

}
