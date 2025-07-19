package com.cvaldezscse.autonomode.constants;

import com.cvaldezscse.autonomode.config.Configuration;

import static com.cvaldezscse.autonomode.constants.TestConstantFunctions.getConfigurationPath;

public class TestConstants {

    public static final String CONFIGURATION_YAML_FILE_PATH = getConfigurationPath();
    public static final Configuration BASE_CONFIG = Configuration.getConfiguration(CONFIGURATION_YAML_FILE_PATH);


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
