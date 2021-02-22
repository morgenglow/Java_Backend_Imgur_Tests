package ru.geekbrains.java4.steps;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import ru.geekbrains.java4.Endpoints;
import ru.geekbrains.java4.Images;
import ru.geekbrains.java4.dto.PostImageResponse;
import ru.geekbrains.java4.utils.FileEncodingUtils;

import static io.restassured.RestAssured.given;
@UtilityClass
public class CommonPostRequest {

    private static final String INPUT_IMAGE_FILE_PATH = Images.POSITIVE.path;

    public static PostImageResponse uploadCommonImage(RequestSpecification spec) {
        RequestSpecification multiPart = spec
                .multiPart(
                        new MultiPartSpecBuilder(FileEncodingUtils.getFileContent(INPUT_IMAGE_FILE_PATH))
                                .controlName("image")
                                .build());
        return given()
                .spec(multiPart)
                .when()
                .post(Endpoints.POST_IMAGE_REQUEST)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(PostImageResponse.class);
    }
}
