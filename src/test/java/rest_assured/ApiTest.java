package rest_assured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    private static final Logger log = LoggerFactory.getLogger(ApiTest.class);

    String API_TOKEN = "Bearer 1db9c9b6c959682be7c96f74ca532c3cb0bd331f46b86a92602f8d319481b6f5";
    String EMAIL = UUID.randomUUID() + "@gmail.com";

    @BeforeTest
    void setup() {
        RestAssured.baseURI = "https://gorest.co.in/";
    }

    @Test
    public void test_user_ids() {
        var matcher = everyItem(
                allOf(
                        notNullValue(),
                        greaterThan(1000000),
                        lessThan(9999999)
                )
        );

        given()
                .when()
                .get("/public/v1/users")
                .then()
                .statusCode(200)
                .body("data.id", matcher);
    }

    @Test
    public void test_create_user() {
        var request = new HashMap<String, Object>();
        request.put("email", EMAIL);
        request.put("name", "test");
        request.put("gender", "male");
        request.put("status", "active");


        given()
                .when()
                .header("Authorization", API_TOKEN)
                .contentType(ContentType.JSON)
                .body(request)
                .post("/public/v1/users")
                .prettyPeek()
                .then()
                .statusCode(201);
    }

    @Test
    public void test_create_user_validation() {
        var request = new HashMap<String, Object>();
        request.put("email", EMAIL);
        request.put("name", "test");
        request.put("gender", "male");
        request.put("status", "active");

        var message = given()
                .when()
                .header("Authorization", API_TOKEN)
                .contentType(ContentType.JSON)
                .body(request)
                .post("/public/v1/users")
                .prettyPeek()
                .then()
                .statusCode(422)
                .extract()
                .body().jsonPath()
                .getString("data[0].message");

        log.info("Message: {}", message);
    }

}
