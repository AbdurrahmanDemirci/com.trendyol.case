package com.trendyol.restAPITest.scenarios.getBookList;

import com.trendyol.restAPITest.constant.Constant;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.*;


public class GetBookList extends Constant {

    private static final Logger logger = Logger.getLogger(com.trendyol.restAPITest.constant.Constant.class);

    @Test
    public void getBookList() {

        logger.info("Web Service RestAPI Test getBookList Started");

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseURI + basePath)
                .then()
                .log().all().extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }
}