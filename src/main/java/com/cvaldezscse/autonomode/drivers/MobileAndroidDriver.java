package com.cvaldezscse.autonomode.drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import static com.cvaldezscse.autonomode.constants.TestConstantFunctions.PLATFORM;
import static com.cvaldezscse.autonomode.constants.TestConstants.*;
import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;
import static java.time.Duration.ofMillis;

public class MobileAndroidDriver {
    private static UiAutomator2Options setCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setAutomationName(ANDROID_UIAUTOMATOR2);
        if (!IS_DEVICEFARM_RUN && IS_LOCAL_RUN) {
            // Local execution specific capabilities
            options.setPlatformName(PLATFORM().name());
            options.setDeviceName(ANDROID_DEVICE_NAME);
            options.setPlatformVersion(ANDROID_OS_VERSION);
            options.setApp(new File(DEFAULT_LOCAL_APK_PATH, ANDROID_APP_FILENAME).getAbsolutePath());
            options.setNewCommandTimeout(ofMillis(80000));
            options.setAdbExecTimeout(ofMillis(20000));
            options.setClearDeviceLogsOnStart(true);
            options.setAutoGrantPermissions(true);
            options.setEnablePerformanceLogging(true);
            options.setFullReset(false);
            options.setNoReset(false);
            options.setCapability("disableIdLocatorAutocompletion", true);
            options.setCapability("unexpectedAlertBehaviour", "dismiss");
            options.setAllowTestPackages(true);
            options.setUninstallOtherPackages("true");
        } else
            System.out.println("Using Device Farm, Most capabilities are managed from there");
        return options;
    }

    public static AndroidDriver getDriver() {
        UiAutomator2Options capabilities = setCapabilities();
        AndroidDriver driver = null;

        try {
            URL serverUrl = getAppiumServerUrl();
            System.out.println("Connecting to Appium at: " + serverUrl);
            driver = new AndroidDriver(serverUrl, capabilities);
            System.out.println("AndroidDriver initialized successfully");
            return driver;
        } catch (MalformedURLException e) {
            System.out.println("Invalid Appium server URL: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Failed to initialize AndroidDriver: " + e.getMessage());
        }
        return driver;
    }
}