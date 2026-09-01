package com.initialrecap.androidstore;

import com.initialrecap.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

//2. Validate item was added to the cart
public class eCommerce_tc_2 extends BaseTest {

    @Test
    public void FillForm() throws InterruptedException {
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/nameField")).sendKeys("Carlos");
        driver.hideKeyboard();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Bahamas\"));"));
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Bahamas']")).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        Thread.sleep(3000);

        //Working with multiple items in the screen
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"LeBron Soldier 12 \"));"));
        int productsNumber = driver.findElements(AppiumBy.id("com.androidsample.generalstore:id/productName")).size();

        List<String> listOfPrices = new ArrayList<>();
        for (int i = 0; i < productsNumber; i++) {
            String current = driver.findElements(AppiumBy.id("com.androidsample.generalstore:id/productName")).get(i).getText();
            System.out.println("Element seen: "+ current);
            if(current.equalsIgnoreCase("LeBron Soldier 12 ")) {
                driver.findElements(AppiumBy.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
            }
        }
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        //Thread.sleep(3000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/toolbar_title")), "text", "Cart"));

        String lastPageProduct = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/productName")).getText();
        Assert.assertEquals(lastPageProduct, "LeBron Soldier 12 ");
    }

}
