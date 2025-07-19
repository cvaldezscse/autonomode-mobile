package com.cvaldezscse.autonomode.constants;

import java.net.MalformedURLException;
import java.net.URL;

import static com.cvaldezscse.autonomode.constants.TestConstants.BASE_CONFIG;

public class TestConstantFunctions {
    public static String getConfigurationPath() {
        String fileName = CONFIGURATION_YAML_FILE_NAME + ".yaml";
        String path = ResourceLoader.getResourcePath(fileName);
        // If not found, try with common prefixes
        if (path == null) {
            // For Device Farm, the file might be in the root directory
            if (IS_DEVICEFARM_RUN)
                return fileName;
            // For local environment, use traditional path
            return "src/main/resources/" + fileName;
        }
        return path;
    }

    public static String getApkPath() {
        if (IS_DEVICEFARM_RUN) {
            return "./apk/";
        } else {
            return CURRENT_PROJECT_PATH + "/src/main/resources/apk/";
        }
    }

    public static String getIpaPath() {
        if (IS_DEVICEFARM_RUN) {
            return "./ipa/";
        } else {
            return CURRENT_PROJECT_PATH + "/src/main/resources/ipa/";
        }
    }

    public static Enums.PlatformType PLATFORM() {
        switch (BASE_CONFIG.getExecutionPlatform().toLowerCase()) {
            case "ios":
                return Enums.PlatformType.iOS;
            case "android":
                return Enums.PlatformType.Android;
            default:
                return Enums.PlatformType.Unknown;
        }
    }

    public static URL getAppiumServerUrl() throws MalformedURLException {
        if (IS_DEVICEFARM_RUN) {
            return new URL(DEVICE_FARM_APPIUM_URL);
        } else {
            return new URL(DEFAULT_LOCAL_APPIUM_URL);
        }
    }
}
