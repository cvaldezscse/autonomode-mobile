package com.cvaldezscse.autonomode.constants;

import static com.cvaldezscse.autonomode.constants.TestConstants.BASE_CONFIG;

public class ConstantFunctions {
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
}
