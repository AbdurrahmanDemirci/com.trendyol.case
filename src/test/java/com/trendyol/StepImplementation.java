package com.trendyol;

import com.trendyol.driver.DriverCreater;
import com.trendyol.methods.Methods;
import org.apache.log4j.PropertyConfigurator;
import com.thoughtworks.gauge.*;


public class StepImplementation extends DriverCreater {

    Methods methods;

    public StepImplementation() {

        PropertyConfigurator.configure(StepImplementation.class.getClassLoader().getResource("log4j.properties"));
        methods = new Methods(driver);
    }


    @Step("<long> wait milliseconds")
    public void waitByMilliSeconds(long milliseconds) {

        methods.waitByMilliSeconds(milliseconds);
    }

    @Step("Click on the <key> element")
    public void clickElement(String key) {

        methods.clickElement(key);
    }

    @Step("Click if <key> element is created")
    public void ifThereClickTheElement(String key) {

        methods.ifThereClickTheElement(key);
    }

    @Step("Write value <text> to element <key>")
    public void writeTextiniToElement(String text, String key) {

        methods.writeTextToElement(text, key);
    }

    @Step("Check if current URL contains the value <expectedURL>")
    public void checkURLContains(String expectedURL) {

        methods.checkURLContains(expectedURL);
    }

    @Step("Check if element <key> contains text <expectedText>")
    public void checkElementContainsText(String key, String expectedText) {

        methods.checkElementContainsText(key, expectedText);
    }

    @Step("Check getAttribute src <element>")
    public void checkGetAttributeSrc(String element) {

        methods.checkGetAttributeSrc(element);
    }
}
