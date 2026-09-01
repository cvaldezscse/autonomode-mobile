package com.initialrecap.androidstore;

import com.initialrecap.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

//4. Hybrid app (includes web browser attached to the screen
public class eCommerce_tc_4_hybrid extends BaseTest {

    @Test
    public void FillForm() throws InterruptedException {
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/nameField")).sendKeys("Carlos");
        driver.hideKeyboard();
        driver.findElement(AppiumBy
                .id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Bahamas\"));"));
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Bahamas']")).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        //Start of the test:
        driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click();
        driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click(); //Due to the label change in the screen

        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/toolbar_title")), "text", "Cart"));

        //In the cart screen
        List<WebElement> productPrices = driver.findElements(AppiumBy.id("com.androidsample.generalstore:id/productPrice"));
        int productPricesCount = productPrices.size();
        double totalSum = 0;
        for (int i = 0; i < productPricesCount; i++) {
            String priceText = productPrices.get(i).getText();
            double formattedPrice = getFormattedAmount(priceText);
            totalSum = totalSum + formattedPrice;
        }
        System.out.println("SUM OF PRODUCTS -> " + totalSum);

        //Price Validation
        double lastPageProduct = getFormattedAmount(driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/totalAmountLbl")).getText());
        System.out.println("TOTAL FROM GENERAL LABEL ->" + lastPageProduct);
        Assert.assertEquals(lastPageProduct, totalSum);

        WebElement termsAndConditions = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/termsButton"));
        longPressAction(termsAndConditions);
        driver.findElement(AppiumBy.id("android:id/button1")).click();
        driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
        Thread.sleep(2000);
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(6000);

        //Hybrid app -> Contains access to web
        Set<String> contexts = driver.getContextHandles();
        for (String contextName: contexts) {
            System.out.println("Context -> "+contextName);
        }


        driver.context("WEBVIEW_com.androidsample.generalstore"); //Chromedriver
        //browser level from now
        driver.findElement(By.name("q")).sendKeys("appium mcp and how to implement it");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.context("NATIVE_APP");

    }

}
