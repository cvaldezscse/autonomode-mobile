package com.cvaldezscse.autonomode.drivers;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.ios.options.wda.ProcessArguments;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.remote.http.HttpMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import static com.cvaldezscse.autonomode.constants.TestConstantFunctions.PLATFORM;
import static com.cvaldezscse.autonomode.constants.TestConstantFunctions.getAppiumServerUrl;
import static com.cvaldezscse.autonomode.constants.TestConstants.*;
import static java.time.Duration.ofMillis;

public class MobileIosDriver {
    private static final int MAX_SESSION_ATTEMPTS = 3;
    private static final int SESSION_RETRY_DELAY_SECONDS = 5;


    /**
     * Sets iOS capabilities based on execution environment.
     * For local execution, configures detailed driver settings.
     * For Device Farm, relies on Device Farm's provided capabilities.
     *
     * @return Configured XCUITestOptions
     */
    private static XCUITestOptions setCapabilities() {
        XCUITestOptions options = new XCUITestOptions();
        options.setAutomationName(AutomationName.IOS_XCUI_TEST);
        if (!IS_DEVICEFARM_RUN) {
            // Local execution specific capabilities
            options.setPlatformName(PLATFORM().name());
            options.setDeviceName(IOS_DEVICE_NAME);
            options.setPlatformVersion(IOS_PLATFORM_VERSION);
            options.setUdid(IOS_DEVICE_UID);
            options.setBundleId(IOS_BUNDLE_ID);
            options.setAutoDismissAlerts(true);
            options.setAutoAcceptAlerts(false);
            options.setMaxTypingFrequency(10);

            // WDA settings
            options.setWdaLaunchTimeout(ofMillis(500000));
            options.setWaitForQuiescence(false);
            options.setSimpleIsVisibleCheck(true);
            options.setReduceMotion(true);
            options.setShowXcodeLog(true);
            options.setShowIosLog(true);
            options.setUseNewWDA(true);
            options.setDerivedDataPath("/Users/carlos.valdez/Documents/mobile-automation-resources/WebDriverAgent/DerivedData");
            options.setNoReset(true);

            // Font settings if enabled
            if (IS_FONT_SETUP_ENABLED) {
                options.setKeepKeyChains(false);
                ProcessArguments arguments = new ProcessArguments(
                        Arrays.asList("-UIPreferredContentSizeCategoryName", FONT_SETTING));
                options.setProcessArguments(arguments);
            }
        } else
            System.out.println("Using Device Farm, Most capabilities are managed from there");
        return options;
    }


    private static XCUITestOptions setCommonCapabilities() {
        XCUITestOptions options = new XCUITestOptions();
        if (!IS_DEVICEFARM_RUN) {
            options.setPlatformName(PLATFORM().name());
            options.setDeviceName(IOS_DEVICE_NAME);
            options.setPlatformVersion(IOS_PLATFORM_VERSION);
            options.setAutomationName(AutomationName.IOS_XCUI_TEST);
            options.setWdaLaunchTimeout(ofMillis(500000));
            options.setUdid(IOS_DEVICE_UDID);
            options.setNoReset(true);
            options.setBundleId(IOS_BUNDLE_ID);
            options.setWaitForQuiescence(false);
            options.setSimpleIsVisibleCheck(true);
            options.setReduceMotion(true);
            options.setShowXcodeLog(true);
            options.setShowIosLog(true);
            options.setUseNewWDA(true);
            options.setDerivedDataPath("/Users/carlos.valdez/Documents/mobile-automation-resources/WebDriverAgent/DerivedData");
            options.setAutoDismissAlerts(true);
            options.setAutoAcceptAlerts(false);
            options.setMaxTypingFrequency(120);
        }

        // WebDriverAgent config
//        options.setWdaLaunchTimeout(Duration.ofSeconds(120));
//        options.setWdaStartupRetries(4);
//        options.setWdaStartupRetryInterval(Duration.ofSeconds(20));

        //Device config

        //Signing
//        options.setCapability("xcodeOrgId", XCODE_ORG_ID);
//        options.setCapability("xcodeSigningId", XCODE_SIGNING_ID);

//
//        // WDA port and retries for stability
//        options.setUseNewWDA(false);
//        options.setCapability("skipServerInstallation", true);
//        options.setCapability("skipDeviceInitialization", true);
//        options.setWdaLocalPort(8100);
//        options.setWdaStartupRetries(4);
//        options.setWdaStartupRetryInterval(Duration.ofSeconds(20));
//        options.setCapability("preventWDAIdleTimeout", true);
//        options.setCapability("xcodeOrgId", XCODE_ORG_ID);
//        options.setCapability("xcodeSigningId", XCODE_SIGNING_ID);
//        options.setUpdatedWdaBundleId("com.cvaldezscseirhythm.WebDriverAgentRunner");
//
//
//        //Timeouts
//        options.setWdaLaunchTimeout(Duration.ofSeconds(120));
//        options.setWdaStartupRetries(4);
//        options.setWdaStartupRetryInterval(Duration.ofSeconds(20));
//        //Device config
//        options.setUdid(IOS_DEVICE_UID);
//        options.setBundleId(IOS_BUNDLE_ID);

        //Other Configs
//        options.setCapability("autoGrantPermissions", true);

        //alert management
        options.setAutoDismissAlerts(true);
        options.setAutoAcceptAlerts(false);
        options.setMaxTypingFrequency(10);
        //options.setNoReset(true);

        if (IS_FONT_SETUP_ENABLED) {
            options.setKeepKeyChains(false);
            ProcessArguments arguments = new ProcessArguments(Arrays.asList("-UIPreferredContentSizeCategoryName", FONT_SETTING));
            options.setProcessArguments(arguments);
        }
        return options;
    }

    /**
     * Creates and returns an IOSDriver instance configured for the current execution environment.
     * Supports both local execution and AWS Device Farm execution modes.
     *
     * @return Configured IOSDriver instance or null if initialization fails
     */
    public static IOSDriver getDriver() {
        XCUITestOptions capabilities = setCapabilities();
        IOSDriver driver = null;

        try {
            URL serverUrl = getAppiumServerUrl();
            System.out.println("Connecting to Appium at: " + serverUrl);
            driver = new IOSDriver(serverUrl, capabilities);

            // Configure vision command if required
            if (REQUIRES_VISION) {
                driver.addCommand(
                        HttpMethod.POST,
                        String.format("/session/%s/appium/vision", driver.getSessionId()),
                        "getVisionText"
                );
                System.out.println("Vision commands enabled");
            }

            System.out.println("IOSDriver initialized successfully");
            return driver;
        } catch (MalformedURLException e) {
            System.out.println("Invalid Appium server URL: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Failed to initialize IOSDriver: " + e.getMessage());
        }

        return driver;
    }
}
