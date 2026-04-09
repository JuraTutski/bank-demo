package test;

import base.BaseDbTest;
import models.UserResponse;
import org.junit.jupiter.api.Test;
import steps.UserApiSteps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserApiTest extends BaseDbTest {

    private final UserApiSteps userApiSteps = new UserApiSteps("http://localhost:8080");

    @Test
    void shouldReturnSameUserAsInDb() throws Exception {
        long userId = 1L;

        String expectedName;
        String expectedEmail;
        Integer expectedAge;

        String sql = "SELECT name, email, age FROM users WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new IllegalStateException("Нет пользователя с id = " + userId);
                }
                expectedName = resultSet.getString("name");
                expectedEmail = resultSet.getString("email");
                expectedAge = resultSet.getInt("age");
            }
        }

        UserResponse userResponse = userApiSteps.getUserById(userId);

        System.out.println("USER FROM DB:  id=" + userId +
                ", name=" + expectedName +
                ", email=" + expectedEmail+
                ", age=" + expectedAge);

        System.out.println("USER FROM API: " + userResponse);


        assertEquals(userId, userResponse.getId(), "id пользователя должен совпадать");
        assertEquals(expectedName, userResponse.getName(), "name должен совпадать с БД");
        assertEquals(expectedEmail, userResponse.getEmail(), "email должен совпадать с БД");
    }
}
