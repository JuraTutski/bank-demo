package base;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class DatabaseSmokeTest extends BaseDbTest {

    @Test
    void testDbConnection() throws Exception {
        String sql = "SELECT * FROM users";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                System.out.println(name);
            }
        }
    }
}
