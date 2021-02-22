package ru.geekbrains.java4;

import com.github.javafaker.Faker;
import io.qameta.allure.Feature;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.java4.utils.FileEncodingUtils;

import static io.restassured.RestAssured.given;

@Feature("")
public class PostImageTests_negative_mp3 extends BaseTest {
    static final String filePath =Images.SONG.path;
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
        given()
                .spec(uploadReqSpec)
                .expect()
                .when()
                .post(Endpoints.POST_IMAGE_REQUEST)
                .prettyPeek()
                .then()
                .spec(responseSpecNegative);
    }
}
