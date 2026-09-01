package com.initialrecap.androiddemo;

import com.google.common.collect.ImmutableMap;
import com.initialrecap.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

//1. Device Rotation
//2. Clipboard
//3. Pressing other keys in the device
public class MiscellaneousAppiumActions extends BaseTest {

    @Test
    public void Miscellaneous() {

        //Go directly to the screen without clicking other screens
        //App package -> App Name/Global Name
        //App activity -> Can be defined as screen
        //Useful to open an app in a specified activity
        //Command to know which is the activity we are located at
        // $ adb shell dumpsys window | grep -E 'mCurrentFocus' -> Mac/Linux
        // $ adb shell dumpsys window | find -E 'mCurrentFocus' -> Windows
        // Output:
        // mCurrentFocus=Window{a09c0e1 u0 io.appium.android.apis/io.appium.android.apis.preference.PreferenceDependencies}
        // Anything before the slash is packageName, everything after is the activityName
        ((JavascriptExecutor) driver).executeScript("mobile: startActivity",
                ImmutableMap.of("intent", "io.appium.android.apis/io.appium.android.apis.preference.PreferenceDependencies"));

        //driver.findElement(AppiumBy.accessibilityId("Preference")).click();
        //driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='3. Preference dependencies']")).click();
        driver.findElement(AppiumBy.id("android:id/checkbox")).click();

        //Rotation
        DeviceRotation landscape = new DeviceRotation(0,0,90);
        driver.rotate(landscape);

        driver.findElement(AppiumBy.xpath("(//android.widget.RelativeLayout)[2]")).click();
        String alertTitle = driver.findElement(AppiumBy.id("android:id/alertTitle")).getText();
        Assert.assertEquals(alertTitle, "WiFi settings");


        //Clipboard Copy-Paste
        //Copy
        driver.setClipboardText("carlos @ wifi");
        //Paste
        driver.findElement(AppiumBy.id("android:id/edit")).sendKeys(driver.getClipboardText());
        //Keypress
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        driver.findElements(AppiumBy.className("android.widget.Button")).get(1).click();


        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.pressKey(new KeyEvent(AndroidKey.HOME));

        System.out.println("Correct");








    }
}
