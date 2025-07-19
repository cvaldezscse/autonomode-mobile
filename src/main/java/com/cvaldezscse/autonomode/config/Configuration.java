package com.cvaldezscse.autonomode.config;


import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

@Getter
@Setter
public class Configuration {

    private String executionPlatform;
    private String htmlReportFileName;
    private String executionType;
    private String environment;
    private String reportMarkupTitle;
    private String retries;
    private String androidDeviceName;
    private String androidPlatformVersion;
    private String androidAppFileName;
    private String androidBundleId;
    private boolean localServerDynamicallyExecuted;
    private String appiumServerUrl;
    private String iosAppVersion;
    private String iosAppBuild;
    private String iosDeviceName;
    private String iosAppFileName;
    private String iosDeviceUID;
    private String iosPlatformVersion;
    private String iosBundleId;
    private String xcodeOrgId;
    private String xcodeSigningId;
    private String androidAppVersion;
    private String androidAppBuild;
    private String defaultFontSetting;
    private String buildKey;
    private String bambooBuildId;
    private JamaConfiguration jamaConfiguration;
    private String myZioUsqa1Host;
    private String myZioUkqa1Host;
    private String projectRepositoryUrl;
    private String appiumCustomPath;
    private int localDbPort;
    private boolean deviceFarmRun = false;
    private ReportConfig reportConfig = new ReportConfig();


    public static Configuration getConfiguration(String configFileLocation) {
        Configuration config = null;
        Yaml yaml = new Yaml();
        // Extract just the filename if a full path is provided
        String fileName = configFileLocation;
        if (fileName.contains("/"))
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        try {
            InputStream in = ResourceLoader.getResourceAsStream(configFileLocation);
            if (in == null) {
                in = ResourceLoader.getResourceAsStream(fileName);
                logInfo("Trying to load configuration from: " + fileName, logger);
            }
            if (in == null) {
                logError("Unable to load configuration from: " + configFileLocation, logger);
                throw new IOException("Unable to load configuration from: " + configFileLocation);
            }
            config = yaml.loadAs(in, Configuration.class);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Initialize with defaults to avoid NPE
            config = new Configuration();
        }
        return config;
    }


}
