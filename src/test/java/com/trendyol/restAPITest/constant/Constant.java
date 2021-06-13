package com.trendyol.restAPITest.constant;

import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import static com.trendyol.utils.PropertyUtils.loadProperties;
import static io.restassured.RestAssured.*;


public class Constant {

    private static final Logger logger = Logger.getLogger(com.trendyol.restAPITest.constant.Constant.class);

    @BeforeTest
    public void launchWebApplication() {

        loadProperties();
        RestAssured.baseURI = "https://60c65d0319aa1e001769f2ec.mockapi.io/";
        RestAssured.basePath = "api/v2/book";

        logger.info("Launch Mockapi Web Application | " + baseURI);
        logger.info("Rest Assured Web Service API Test Started ");
        logger.info("****************************************************************************************************" + "\r\n");
        logger.info("---------------------------------------------TEST PLAN----------------------------------------------" + "\r\n");
    }


    @AfterTest
    public void afterTest() {

        logger.info("====================================================================================================" + "\r\n");
        logger.info("_________________________________________SCENARIO FINISHED_________________________________________" + "\r\n");
    }
}