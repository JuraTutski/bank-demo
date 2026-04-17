package base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class PaymentsDbClient {

    private static final String HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String PORT = System.getenv().getOrDefault("DB_PORT", "5432");
    private static final String DB   = System.getenv().getOrDefault("DB_NAME", "payments_db");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "test_user");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "test_password");

    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB;

    public boolean existsPayment(int amount, String status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM payments WHERE amount = ? AND status = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, amount);
            ps.setString(2, status);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public void insertTestPayment(int amount, String status) throws SQLException {
        String sql = "INSERT INTO payments (client_id, amount, status) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "test-client");
            ps.setInt(2, amount);
            ps.setString(3, status);
            ps.executeUpdate();
        }
    }

    public void clearPayments() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = conn.createStatement()) {

            st.executeUpdate("DELETE FROM payments");
        }
    }
}