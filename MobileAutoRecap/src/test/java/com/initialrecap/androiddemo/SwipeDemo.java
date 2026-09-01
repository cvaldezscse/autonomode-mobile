package com.initialrecap.androiddemo;

import com.initialrecap.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SwipeDemo extends BaseTest {

    @Test
    public void SwipeDemoTest() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='1. Photos']")).click();
        WebElement firstImage = driver.findElement(AppiumBy.xpath("(//android.widget.ImageView)[1]"));
        Assert.assertEquals(firstImage.getAttribute("focusable"), "true");
        swipeAction(firstImage, "left");
        Assert.assertEquals(firstImage.getAttribute("focusable"), "false");

    }



}
