package com.cvaldezscse.autonomode.drivers;

import io.appium.java_client.AppiumDriver;

import static com.cvaldezscse.autonomode.constants.TestConstantFunctions.PLATFORM;

public class Driver {
    public static AppiumDriver getAppiumDriver() {
        AppiumDriver driver = null;
        switch (PLATFORM()) {
            case iOS :
                driver = new MobileIosDriver().getDriver();
                break;
            case Android:
                driver = new MobileAndroidDriver().getDriver();
                break;
            default:
                throw new RuntimeException("EXCEPTION_NO_VALID_PLATFORM_SELECTED");
        }
        return driver;
    }
}