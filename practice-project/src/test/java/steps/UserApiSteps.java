package steps;

import io.restassured.http.ContentType;
import models.UserResponse;

import static io.restassured.RestAssured.given;

public class UserApiSteps {

    private final String baseUrl;

    public UserApiSteps(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public UserResponse getUserById(long id) {
        return given()
                .baseUri(baseUrl)
                .accept(ContentType.JSON)
                .when()
                .get("/users/" + id)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .as(UserResponse.class);
    }
}
