package com.trendyol.driver;


import org.apache.commons.io.*;
import org.apache.commons.lang3.*;
import org.slf4j.*;
import org.slf4j.Logger;
import com.thoughtworks.gauge.*;
import org.openqa.selenium.*;

import java.io.*;
import java.text.*;
import java.util.*;

import static com.trendyol.utils.PropertyUtils.loadProperties;


public class DriverCreater {

    protected static WebDriver driver;
    private static String browserName = "chrome";
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String baseUrl = "https://www.trendyol.com/";

    @BeforeSuite
    public void beforeSuite() {

        loadProperties();
        logger.info("****************************************************************************************************" + "\r\n");
        logger.info("---------------------------------------------TEST PLAN----------------------------------------------" + "\r\n");
    }


    @BeforeSpec
    public void beforeSpec(ExecutionContext executionContext) {

        logger.info("====================================================================================================" + "\r\n");
        logger.info("------------------------------------------------SPEC------------------------------------------------" + "\r\n");
        logger.info("SPEC FILE NAME: " + executionContext.getCurrentSpecification().getFileName());
        logger.info("SPEC NAME: " + executionContext.getCurrentSpecification().getName());
    }


    @BeforeScenario
    public void beforeScenario(ExecutionContext executionContext) throws Exception {

        logger.info("____________________________________________________________________________________________________" + "\r\n");
        logger.info("---------------------------------------------SCENARIO----------------------------------------------" + "\r\n");
        logger.info("SCENARIO NAME: " + executionContext.getCurrentScenario().getName());
        logger.info("SCENARIO TAG: " + executionContext.getCurrentScenario().getTags().toString());

        String key = System.getenv("key");
        if (StringUtils.isEmpty(key)) {

            driver = LocalBrowserExec.LocalExec(browserName);
        }
        driver.get(baseUrl);
    }


    @BeforeStep
    public void beforeStep(ExecutionContext executionContext) {

        logger.info(executionContext.getCurrentStep().getDynamicText());
    }


    @AfterStep
    public void afterStep(ExecutionContext executionContext) throws Exception {

        if (executionContext.getCurrentStep().getIsFailing()) {

            logger.error("FAİL SPEC FILE NAME: " + executionContext.getCurrentSpecification().getFileName());
            logger.error("FAİL SPEC NAME: " + executionContext.getCurrentScenario().getName());
            logger.error("FAİL STEP: " + executionContext.getCurrentStep().getDynamicText());
            logger.error("FAİL MESSAGE: " + executionContext.getCurrentStep().getErrorMessage() + "\r\n"
                    + executionContext.getCurrentStep().getStackTrace());
        }

        if (executionContext.getCurrentStep().getIsFailing()) {

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            File fileName = new File(dateFormat.format(date) + ".png");

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File targetFile = new File(System.getProperty("user.dir") + "/lib/screenshot/ssImage/error-Scenario:"
                    + executionContext.getCurrentScenario().getName().replace(" ", "") + fileName);
            FileUtils.copyFile(srcFile, targetFile);
        }
    }


    @AfterScenario
    public void afterScenario(ExecutionContext executionContext) {

        quitDriver();
        if (executionContext.getCurrentScenario().getIsFailing()) {

            logger.info("_____________________________________________TEST FAİL_____________________________________________" + "\r\n");
        } else {

            logger.info("__________________________________________TEST SUCCESSFULL_________________________________________" + "\r\n");
        }
        logger.info("____________________________________________________________________________________________________" + "\r\n");
    }


    @AfterSpec
    public void afterSpec() {

        logger.info("====================================================================================================" + "\r\n");
    }


    private void quitDriver() {

        if (driver != null) {
            driver.quit();
            logger.info("_________________________________________SCENARIO FINISHED_________________________________________" + "\r\n");
        }
    }
}
