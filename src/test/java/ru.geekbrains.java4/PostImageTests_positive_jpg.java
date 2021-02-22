package ru.geekbrains.java4;

import com.github.javafaker.Faker;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.java4.utils.FileEncodingUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@Feature("")
public class PostImageTests_positive_jpg extends BaseTest {
    static final String filePath =Images.POSITIVE.path;
    private String uploadedImageId;
    MultiPartSpecification multiPartSpec;
    RequestSpecification uploadReqSpec;
    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        byte[] fileContent = FileEncodingUtils.getFileContent(filePath);
        multiPartSpec = new MultiPartSpecBuilder(fileContent)
                .controlName("image")
                .build();
        uploadReqSpec = reqSpec.multiPart(multiPartSpec)
                .formParam("title", faker.harryPotter().character())
                .formParam("description", faker.harryPotter().quote());
    }

    @Test
    void uploadFileTest() {
        uploadedImageId = given()
                .spec(uploadReqSpec)
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @AfterEach
    @Step("Удалить файл после теста")
    void tearDown() {
        given()
                .headers("Authorization", token)
                .when()
                .delete("/account/{username}/image/{deleteHash}", username, uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);

    }


}
