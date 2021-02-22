package ru.geekbrains.java4;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static ru.geekbrains.java4.steps.CommonPostRequest.uploadCommonImage;

public class UpdateImageTests_Negative_without_Auth extends BaseTest {
    String deletehash;
    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        deletehash = uploadCommonImage(reqSpec).getData().getDeletehash();
    }

    @Test
    void updateImgTest() {
        given()
                .spec(withoutAuthReqSpec)
                .multiPart("title", faker.harryPotter().character())
                .multiPart("description", faker.harryPotter().quote())
                .when()
                .post(Endpoints.GET_IMAGE_REQUEST,deletehash)
                .prettyPeek()
                .then()
                .spec(responseSpecNegativeAuth);
    }

    @AfterEach
    void tearDown() {
        given()
                .spec(reqSpec)
                .delete(Endpoints.DELETE_IMAGE_REQUEST, username, deletehash)
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }
}
