package com.initialrecap.androidstore;

import com.initialrecap.BaseTest;
import io.appium.java_client.AppiumBy;
import org.testng.Assert;
import org.testng.annotations.Test;

// 1. Initial form validation
public class eCommerce_tc_1 extends BaseTest {

    @Test
    public void FillForm() throws InterruptedException {
       //driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/nameField")).sendKeys("Carlos Valdez");
        driver.hideKeyboard();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/radioMale")).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/radioFemale")).click();

        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Bahamas\"));"));
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Bahamas']")).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        Thread.sleep(3000);

        String toastMessage = driver.findElement(AppiumBy.xpath("(//android.widget.Toast)[1]")).getAttribute("name");
        Assert.assertEquals(toastMessage, "Please enter your name");
    }

}
