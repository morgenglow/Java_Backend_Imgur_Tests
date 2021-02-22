package ru.geekbrains.java4;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class BaseTest {
    static Properties prop = new Properties();
    static String token;
    static String username;
    static ResponseSpecification responseSpecification = null;
    static ResponseSpecification responseSpecificationUpdate;
    static RequestSpecification reqSpec;
    static RequestSpecification withAuthReqSpec;
    static RequestSpecification withoutAuthReqSpec;
    static ResponseSpecification  responseSpecNegative;
    static ResponseSpecification  responseSpecNegativeAuth;
    static RequestSpecification reqSpecUpdate;

    @BeforeAll
    static void beforeAll() {
        loadProperties();

        token = prop.getProperty("token");
        username = prop.getProperty("username");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = prop.getProperty("base.url");
        RestAssured.filters(new AllureRestAssured());

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Access-Control-Allow-Credentials", "true")
                .build();

        responseSpecificationUpdate = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .setAccept(ContentType.ANY)
                .build();

        reqSpecUpdate = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .setAccept(ContentType.ANY)
                .build();

        responseSpecNegative = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectBody("success", is(false))
                .build();

        responseSpecNegativeAuth = new ResponseSpecBuilder()
                .expectStatusCode(403)
                .expectBody("success", is(false))
                .build();

        withoutAuthReqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "")
                .build();

        withAuthReqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .setAccept(ContentType.ANY)
                .log(LogDetail.ALL)
                .build();

    }


    static void loadProperties()  {

        try (InputStream file = new FileInputStream("src/test/resources/application.properties")) {
            prop.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
