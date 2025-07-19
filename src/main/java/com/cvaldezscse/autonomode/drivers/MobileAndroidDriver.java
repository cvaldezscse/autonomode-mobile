package com.cvaldezscse.autonomode.drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;

import static com.cvaldezscse.autonomode.constants.TestConstantFunctions.PLATFORM;
import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;

public class MobileAndroidDriver {
    private static UiAutomator2Options setCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setAutomationName(ANDROID_UIAUTOMATOR2);
        if (!IS_DEVICEFARM_RUN) {
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
            logInfo("Using Device Farm, Most capabilities are managed from there", logger);
        return options;
    }

    public static AndroidDriver getDriver() {
        UiAutomator2Options capabilities = setCapabilities();
        AndroidDriver driver = null;

        try {
            URL serverUrl = getAppiumServerUrl();
            logInfo("Connecting to Appium at: " + serverUrl, logger);
            driver = new AndroidDriver(serverUrl, capabilities);
            logInfo("AndroidDriver initialized successfully", logger);
            return driver;
        } catch (MalformedURLException e) {
            logError("Invalid Appium server URL: " + e.getMessage(), logger);
        } catch (Exception e) {
            logError("Failed to initialize AndroidDriver: " + e.getMessage(), logger);
        }

        return driver;
    }
}