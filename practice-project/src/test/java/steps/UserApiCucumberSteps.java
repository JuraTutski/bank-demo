package steps;

import base.BaseDbTest;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.UserResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserApiCucumberSteps extends BaseDbTest {

    private final UserApiSteps userApiSteps = new UserApiSteps("http://localhost:8080");

    private long userId;
    private String expectedName;
    private String expectedEmail;
    private UserResponse response;

    @Given("в базе есть пользователь с именем {string} и email {string}")
    public void insertUser(String name, String email) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO users (name, email) VALUES (?, ?) RETURNING id, name, email"
                )) {
            statement.setString(1, name);
            statement.setString(2, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new IllegalStateException("Не удалось вставить пользователя в БД");
                }
                userId = resultSet.getLong("id");
                expectedName = resultSet.getString("name");
                expectedEmail = resultSet.getString("email");
            }

        }
    }

    @When("я запрашиваю этого пользователя по API")
    public void getUserByApi(){
        response = userApiSteps.getUserById(userId);
    }

    @Then("в ответе возвращается тот же пользователь")
    public void verifyUser(){
        assertEquals(userId, response.getId(), "id пользователя должен совпадать");
        assertEquals(expectedName, response.getName(), "name должен совпадать с БД");
        assertEquals(expectedEmail, response.getEmail(), "email должен совпадать с БД");
    }

    @After
    public void cleanupUser() throws SQLException{
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE email = 'test@example.com'"
        )) {
            statement.executeUpdate();
        }
    }
}
