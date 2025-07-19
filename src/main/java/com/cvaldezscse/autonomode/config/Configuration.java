package com.cvaldezscse.autonomode.config;


import com.cvaldezscse.autonomode.utils.ResourceLoader;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;
import java.io.IOException;
import java.io.InputStream;

@Getter
@Setter
public class Configuration {
    //General Configs
    private String executionPlatform;
    private String executionType;
    private String environment;
    private String areRetriesAllowed;


    //Test Report Config
    private String htmlReportFileName;
    boolean showFailedTestsFirst = false;
    private String projectRepositoryUrl;
    private String usedTemplate;
    private String logoImage;
    private String reportMarkupTitle;


    //Android Local capabilities
    private String androidDeviceName;
    private String androidPlatformVersion;
    private String androidAppFileName;
    private String androidAppVersion;
    private String androidAppBuild;
    private String androidBundleId;


    //iOS Local capabilities
    private String iosDeviceName;
    private String iosPlatformVersion;
    private String iosDeviceUDID;
    private String xcodeOrgId;
    private String xcodeSigningId;
    private String iosAppFileName;
    private String iosBundleId;
    private String iosAppVersion;
    private String iosAppBuild;


    //Appium Server Local Config
    private boolean localServerDynamicallyStarted;
    private int appiumServerPort;
    private String appiumServerUrl;
    private String appiumServerCustomPath;



    public static Configuration getConfiguration(String configFileLocation) {
        Configuration config = null;
        Yaml yaml = new Yaml();
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
            config = new Configuration();
        }
        return config;
    }


}
