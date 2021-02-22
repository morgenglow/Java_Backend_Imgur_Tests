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
public class PostImageTests_negative_txt extends BaseTest {
    static final String filePath =Images.TXT.path;
    private String uploadedImageId;
    MultiPartSpecification multiPartSpec;
    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        byte[] fileContent = FileEncodingUtils.getFileContent(filePath);
        multiPartSpec = new MultiPartSpecBuilder(fileContent)
                .controlName("image")
                .build();
    }

    @Test
    void uploadFileTest() {
        given()
                .spec(withAuthReqSpec)
                .expect()
                .when()
                .post(Endpoints.POST_IMAGE_REQUEST)
                .prettyPeek()
                .then()
                .spec(responseSpecNegative);
    }
}
