package com.cvaldezscse.autonomode.constants;

import java.net.MalformedURLException;
import java.net.URL;
import static com.cvaldezscse.autonomode.constants.TestConstants.BASE_CONFIG;

public class TestConstantFunctions {


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
