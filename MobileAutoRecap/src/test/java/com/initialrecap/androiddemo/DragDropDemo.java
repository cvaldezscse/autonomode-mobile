package com.initialrecap.androiddemo;

import com.google.common.collect.ImmutableMap;
import com.initialrecap.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DragDropDemo extends BaseTest {

    @Test
    public void DragDropDemoTest() throws InterruptedException {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Drag and Drop")).click();
        WebElement firstDotAkaSource = driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_dot_1"));
        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) firstDotAkaSource).getId(),
                "endX", 600,
                "endY", 600
        ));
        Thread.sleep(3000);
        WebElement resultOfDragDrop = driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_result_text"));
        Assert.assertEquals(resultOfDragDrop.getText(), "Dropped!");
    }



}
