package base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDbTest {

    private static final String HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String PORT = System.getenv().getOrDefault("DB_PORT", "5432");
    private static final String DB   = System.getenv().getOrDefault("DB_NAME", "testdb");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "testuser");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "testpass");

    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB;

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}