package com.trendyol.methods;


import com.trendyol.helper.ElementHelper;
import com.trendyol.helper.StoreHelper;
import com.trendyol.model.ElementInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.HttpClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;


import java.time.Duration;
import java.util.List;
import javax.inject.Inject;


public class Methods {

    private int valueCount;

    @Inject
    WebDriver driver;
    FluentWait<WebDriver> wait;
    private Logger logger = LoggerFactory.getLogger(getClass());


    public Methods(WebDriver driver) {

        this.driver = driver;
        wait = setFluentWait();
    }


    public void waitByMilliSeconds(long milliseconds) {

        try {
            Thread.sleep(milliseconds);
            logger.info("' " + milliseconds + " '" + " => Waited until");
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }


    public void clickElement(String key) {

        findElement(key).click();
        logger.info("' " + key + " '" + " => Element clicked");
    }


    public void ifThereClickTheElement(String key) {

        if (findElement(key).isDisplayed()) {

            clickElement(key);
            logger.info("' " + key + " '" + " => Element clicked");
        } else {

            logger.info("' " + key + " '" + " => The element did not occur");
        }
    }


    public void writeTextToElement(String text, String key) {

        findElement(key).sendKeys(text);
        logger.info("' " + text + " '" + " => Text is writed");
    }


    public void checkURLContains(String expectedURL) {

        String actualURL = driver.getCurrentUrl();
        Assert.assertTrue("Actual URL doesn't match the expected." + "Expected: " + expectedURL + ", Actual: " + actualURL, actualURL.contains(expectedURL));
    }


    public void checkElementContainsText(String key, String expectedText) {

        String actualText = findElement(key).getText();
        Assert.assertTrue("Expected text is not contained" + "Expected: " + expectedText + ", Actual: " + actualText, actualText.contains(expectedText));
    }


    public void checkGetAttributeSrc(String element) {

        try {
            valueCount = 0;
            List<WebElement> getAttributeList = findElements(element);
            logger.info("' " + getAttributeList.size() + " '" + " => Total of src are");

            for (WebElement getAttribute : getAttributeList) {
                if (getAttribute != null) {

                    verifySrcActive(getAttribute);
                }
            }

            logger.info("' " + valueCount + " '" + " => Total of invalid src are");
        } catch (Exception e) {

            e.printStackTrace();
            logger.info(e.getMessage());
        }
    }


    public void verifySrcActive(WebElement srcElement) {

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(srcElement.getAttribute("src"));
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200)

                valueCount++;
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    private WebElement findElement(String key) {

        By by = getBy(key);
        WebElement webElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        if ((!webElement.equals(""))) {

            ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
            By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
            WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
            webElement = webDriverWait
                    .until(ExpectedConditions.presenceOfElementLocated(infoParam));
            ((JavascriptExecutor) driver).executeScript(

                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                    webElement);
        }
        return webElement;
    }


    private List<WebElement> findElements(String key) {

        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        return driver.findElements(infoParam);
    }


    private By getBy(String key) {

        return ElementHelper.getElementInfoToBy(getElementInfo(key));
    }


    private ElementInfo getElementInfo(String key) {

        return StoreHelper.INSTANCE.findElementInfoByKey(key);
    }


    public FluentWait<WebDriver> setFluentWait() {

        FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(3000))
                .ignoring(NoSuchElementException.class);

        return fluentWait;
    }
}