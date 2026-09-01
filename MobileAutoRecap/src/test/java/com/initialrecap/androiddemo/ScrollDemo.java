package com.initialrecap.androiddemo;

import com.initialrecap.BaseTest;
import io.appium.java_client.AppiumBy;
import org.testng.annotations.Test;

public class ScrollDemo extends BaseTest {

    @Test
    public void ScrollDemoTest() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();

        //Scrolling style 1 (when the scroll element is known prior)
        //driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"WebView\"));"));

        //Using the scrolling style 2
        ScrollToEndAction();
    }



}
