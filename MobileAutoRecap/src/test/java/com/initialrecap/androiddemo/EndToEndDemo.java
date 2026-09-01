package com.initialrecap.androiddemo;

import com.initialrecap.BaseTest;
import io.appium.java_client.AppiumBy;
import org.testng.annotations.Test;

//This is an exercise the teacher gives us to go to the app, then App -> AlertDialogs
// And check all the needed stuff to automate and validate according on what we saw in
// previous classes
public class EndToEndDemo extends BaseTest {

    @Test
    public void End2EndDemoTest() {
        driver.findElement(AppiumBy.accessibilityId("App")).click();
        driver.findElement(AppiumBy.accessibilityId("Alert Dialogs")).click();

    }



}
