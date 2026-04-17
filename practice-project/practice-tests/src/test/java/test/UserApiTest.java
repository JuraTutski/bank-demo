package test;

import base.BaseDbTest;
import models.UserResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import steps.UserApiSteps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Disabled("REST users API is not implemented/running now")
public class UserApiTest extends BaseDbTest {

    private final UserApiSteps userApiSteps = new UserApiSteps("http://localhost:8080");

    @Test
    void shouldReturnSameUserAsInDb() throws SQLException {
        long userId;
        String expectedName;
        String expectedEmail;

        // 1. Готовим данные в БД
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO users (name, email) VALUES (?, ?) RETURNING id, name, email"
             )) {
            stmt.setString(1, "Test User");
            stmt.setString(2, "test@example.com");

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalStateException("Не удалось вставить пользователя в БД");
                }
                userId = rs.getLong("id");
                expectedName = rs.getString("name");
                expectedEmail = rs.getString("email");
            }
        }

        // 2. Делаем запрос к API
        UserResponse userResponse = userApiSteps.getUserById(userId);

        // 3. Сравниваем ответ API с данными из БД
        assertEquals(userId, userResponse.getId(), "id пользователя должен совпадать");
        assertEquals(expectedName, userResponse.getName(), "name должен совпадать с БД");
        assertEquals(expectedEmail, userResponse.getEmail(), "email должен совпадать с БД");
    }
}