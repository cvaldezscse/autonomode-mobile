package com.initialrecap.androiddemo;

import com.initialrecap.BaseTest;
import io.appium.java_client.AppiumBy;
import org.testng.Assert;
import org.testng.annotations.Test;


public class AppiumBasics extends BaseTest {

    @Test
    public void WifiSettingsName() {

        //Actual Automation
        //Xpath, accesibilityId, className, androidUIAutomator
        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='3. Preference dependencies']")).click();
        driver.findElement(AppiumBy.id("android:id/checkbox")).click();
        driver.findElement(AppiumBy.xpath("(//android.widget.RelativeLayout)[2]")).click();
        String alertTitle = driver.findElement(AppiumBy.id("android:id/alertTitle")).getText();

        Assert.assertEquals(alertTitle, "WiF settings");

        driver.findElement(AppiumBy.id("android:id/edit")).sendKeys("carlos @ wifi");
        driver.findElements(AppiumBy.className("android.widget.Button")).get(1).click();

        System.out.println("Correct");
    }
}
