package com.initialrecap;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Map;

public class BaseTest {

    public AndroidDriver driver;
    public AppiumDriverLocalService service;

    @BeforeClass
    public void ConfigureAppium() throws URISyntaxException, MalformedURLException {
        this.service = new AppiumServiceBuilder()
                .withAppiumJS(new File("/Users/carlos/.nvm/versions/node/v24.20.0/bin/appium"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
        service.start();
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 6a API 34 Android 14.0 with Google");
        //options.setApp("src/test/java/com/initialrecap/resources/ApiDemos-debug.apk");
        options.setApp("src/test/java/com/initialrecap/resources/General-Store.apk");
        options.setCapability("appium:chromedriverExecutable", System.getProperty("user.home") + "/webdrivers/chromedriver/chromedriver-113");
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    public void longPressAction(WebElement element){
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()
                        , "duration", 2000
                ));
    }

    public void ScrollToEndAction() {
        //Scrolling style 2 (no prior idea)
        boolean canScrollMore;
        Map<String, Object> map = ImmutableMap.of(
                "left", 100,
                "top", 200,
                "width", 200,
                "height", 200,
                "direction", "down",
                "percent", 3.0
        );
        do {
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", map);
        } while (canScrollMore);
    }

    public void swipeAction(WebElement element, String direction){
        //Swipe starts
        Map<String, Object> map = ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction,
                "percent", "0.25"
        );
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", map);
    }

    public double getFormattedAmount(String amount){
        return Double.parseDouble(amount.substring(1));
    }


    @AfterClass
    public void tearDown(){
        driver.quit();
        service.stop();
    }



}
