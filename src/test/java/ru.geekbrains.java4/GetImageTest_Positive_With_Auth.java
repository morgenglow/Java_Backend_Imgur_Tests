package ru.geekbrains.java4;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static ru.geekbrains.java4.steps.CommonPostRequest.uploadCommonImage;

public class GetImageTest_Positive_With_Auth extends BaseTest{
    static RequestSpecification withoutAuthReqSpec;
    static RequestSpecification withAuthReqSpec;
    String deletehash;
    String imgid;
    @BeforeAll
    static void createSpecs() {
        withoutAuthReqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "")
                .build();
        withAuthReqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .log(LogDetail.ALL)
                .build();
    }

    @BeforeEach
    void setUp() {
        deletehash = uploadCommonImage(withAuthReqSpec).getData().getDeletehash();
        imgid = uploadCommonImage(withAuthReqSpec).getData().getId();
    }

    @Test
    void getImageInfoPositiveTest() {
        given()
                .spec(withAuthReqSpec)
                .log()
                .all()
                .when()
                .get(Endpoints.GET_IMAGE_REQUEST, imgid)
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @AfterEach
    @Step("Удалить файл после теста")
    void tearDown() {
        given()
                .headers("Authorization", token)
                .when()
                .delete(Endpoints.DELETE_IMAGE_REQUEST, username, imgid)
                .prettyPeek()
                .then()
                .statusCode(200);

    }
}
