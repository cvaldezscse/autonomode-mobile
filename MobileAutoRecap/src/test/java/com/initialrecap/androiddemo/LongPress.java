package com.initialrecap.androiddemo;

import com.initialrecap.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LongPress extends BaseTest {

    @Test
    public void LongPressGesture() throws InterruptedException {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Expandable Lists']")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();

        WebElement elementToClickOn = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='People Names']"));
        longPressAction(elementToClickOn);
        Thread.sleep(2000);
        WebElement element = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='android:id/title']"));
        Assert.assertEquals(element.getText(), "Sample menu");
        Assert.assertTrue(element.isDisplayed());
    }

}
