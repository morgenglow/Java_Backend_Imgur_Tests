package ru.geekbrains.java4;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.geekbrains.java4.dto.GetAccountResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class GetAccountTests extends BaseTest {
    static RequestSpecification withoutAuthReqSpec;

    @BeforeAll
    static void createSpecs() {
         withoutAuthReqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "")
                .build();

    }


    @Test
    void getAccountInfoPositiveTest() {
        given()
                .spec(reqSpec)
                .log()
                .all()
                .when()
                .get(Endpoints.GET_ACCOUNT_REQUEST, username)
                .prettyPeek()
                .then()
//                .spec(responseSpecification)
        ;
    }

    @Test
    void getAccountInfoNegativeWithoutAuthTest() {
        given()
                .spec(withoutAuthReqSpec)
        .when()
                .get("https://api.imgur.com/3/account/AnnaSmotrova")
        .prettyPeek();
    }
    @Test
    void getAccountInfoPositiveWithManyChecksTest() {
        given()
                .headers("Authorization", token)
                .expect()
                .body(CoreMatchers.containsString(username))
                .body("success", is(true))
                .when()
                .get("/account/{username}", username)
                .then()
                .statusCode(200);
    }

    @Test
    void getAccountInfoPositiveWithObjectTest() {
        GetAccountResponse response = given()
                .when()
                .get("https://api.imgur.com/3/account/AnnaSmotrova")
//                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(GetAccountResponse.class);
        System.out.println(response.getStatus().toString());
        assertThat(response.getStatus(), equalTo(200));
        assertThat(response.getData().getUrl(), equalTo("AnnaSmotrova"));

//       log.info(response.getStatus().toString());
    }


}
