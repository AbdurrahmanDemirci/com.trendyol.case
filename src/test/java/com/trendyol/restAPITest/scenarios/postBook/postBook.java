package com.trendyol.restAPITest.scenarios.postBook;

import com.trendyol.restAPITest.constant.Constant;
import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;


public class postBook extends Constant {

    private static final Logger logger = Logger.getLogger(com.trendyol.restAPITest.constant.Constant.class);

    @Test
    public void addAuthorNameAndBook() {

        logger.info("Web Service RestAPI Test addAuthorNameAndBook Started");

        String requestBody = "{\n" +
                "  \"author\": \"Stefan Zweig\",\n" +
                "  \"title\": \"Satranç\" \n}";


        Response response2 = given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseURI + basePath)
                .then()
                .log().all().extract().response();

        Assertions.assertEquals(200, response2.statusCode());

        if (response2.jsonPath().getString("author").contains("Stefan Zweig")) {
            if (response2.jsonPath().getString("title").contains("Satranç")) {

                Assertions.fail("There is an author and a book that matches this information, it cannot be added");
            }
        } else {

            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .body(requestBody)
                    .post(baseURI + basePath)
                    .then()
                    .log().all().extract().response();

            Assertions.assertEquals(201, response.statusCode());
        }
    }
}