package com.cvaldezscse.autonomode.constants;

import com.cvaldezscse.autonomode.config.Configuration;
import java.nio.file.Paths;

public class TestConstants {

    public static final String DEFAULT_CONFIG_FILE_PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "config", "configuration.yaml").toString();
    public static final Configuration BASE_CONFIG = Configuration.getConfiguration();

    /**
     * General Configs
     */
    public static final String EXECUTION_TYPE = BASE_CONFIG.getExecutionType();
    public static final String ENVIRONMENT = BASE_CONFIG.getEnvironment();
    public static final boolean ARE_RETRIES_ALLOWED = BASE_CONFIG.isAreRetriesAllowed();
    public static final boolean IS_DEVICEFARM_RUN  = EXECUTION_TYPE.equals("aws");
    public static final boolean IS_LOCAL_RUN = EXECUTION_TYPE.equals("local");

    /**
     * Test Report
     */
    public static final String HTML_REPORT_FILE_NAME = BASE_CONFIG.getHtmlReportFileName();
    public static final boolean SHOW_FAILED_TESTS_FIRST = BASE_CONFIG.isShowFailedTestsFirst();
    public static final String PROJECT_REPOSITORY_URL = BASE_CONFIG.getProjectRepositoryUrl();
    public static final String USED_TEMPLATE = BASE_CONFIG.getUsedTemplate();
    public static final String LOGO_IMAGE = BASE_CONFIG.getLogoImageName();
    public static final String REPORT_MARKUP_TITLE = BASE_CONFIG.getReportMarkupTitle();


    /**
     * Android Local
     */
    public static final String ANDROID_DEVICE_NAME = BASE_CONFIG.getAndroidDeviceName();
    public static final String ANDROID_OS_VERSION = BASE_CONFIG.getAndroidPlatformVersion();
    public static final String ANDROID_APP_FILENAME = BASE_CONFIG.getAndroidAppFileName();
    public static final String ANDROID_APP_VERSION = BASE_CONFIG.getAndroidAppVersion();
    public static final String ANDROID_APP_BUILD = BASE_CONFIG.getAndroidAppBuild();
    public static final String ANDROID_BUNDLE_ID = BASE_CONFIG.getAndroidBundleId();


    /**
     * iOS Local
     */
    public static final String IOS_DEVICE_NAME = BASE_CONFIG.getIosDeviceName();
    public static final String IOS_PLATFORM_VERSION = BASE_CONFIG.getIosPlatformVersion();
    public static final String IOS_DEVICE_UDID = BASE_CONFIG.getIosDeviceUDID();
    public static final String XCODE_ORG_ID = BASE_CONFIG.getXcodeOrgId();
    public static final String XCODE_SIGNING_ID = BASE_CONFIG.getXcodeSigningId();
    public static final String IOS_APP_FILE_NAME = BASE_CONFIG.getIosAppFileName();
    public static final String IOS_BUNDLE_ID = BASE_CONFIG.getIosBundleId();
    public static final String IOS_APP_VERSION = BASE_CONFIG.getIosAppVersion();


    /**
     * AppiumServer
     */
    public static final boolean LOCAL_SERVER_AUTOSTART = BASE_CONFIG.isLocalServerDynamicallyStarted();
    public static final int APPIUM_LOCAL_SERVER_PORT = BASE_CONFIG.getAppiumServerPort();
    public static final String APPIUM_LOCAL_URL = BASE_CONFIG.getAppiumServerUrl();
    public static final String APPIUM_SERVER_CUSTOM_PATH = BASE_CONFIG.getAppiumServerCustomPath();





    public static final String DEVICE_FARM_APPIUM_URL = "http://127.0.0.1:4723/wd/hub";
    public static final String OFFLINE_TEST_CASES_PATH = "src/main/resources/TestInfo.json";
    public static final String EMPTY_STRING = "";
    public static final String STRING_WILDCARD = "%s";
    public static final String REPLACEMENT_NUMBER_WILDCARD = "%d";
    public static final int ZERO_INDEX = 0;
    public static final int WAIT_TIME_ONE_SEC = 1;
    public static final int WAIT_TIME_SHORT = 3;
    public static final int WAIT_TIME_MEDIUM = 5;
    public static final int WAIT_TIME_LONG = 10;


}
