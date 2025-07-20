package com.cvaldezscse.autonomode.config;

import com.cvaldezscse.autonomode.constants.TestConstants;
import com.cvaldezscse.autonomode.utils.ResourceLoader;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Optional;

@Getter
@Setter
public class Configuration {

    //General Configs
    private String executionPlatform;
    private String executionType;
    private String environment;
    private boolean areRetriesAllowed;


    //Test Report Config
    private String htmlReportFileName;
    boolean showFailedTestsFirst = false;
    private String projectRepositoryUrl;
    private String usedTemplate;
    private String logoImageName;
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
        Yaml yaml = new Yaml();
        String fileName = Paths.get(configFileLocation).getFileName().toString();
        try (InputStream in = Optional
                .ofNullable(ResourceLoader.getResourceAsStream(configFileLocation))
                .or(() -> Optional.ofNullable(ResourceLoader.getResourceAsStream(fileName)))
                .orElse(null)) {
            if (in == null) {
                System.out.println("Configuration file not found: " + configFileLocation);
                return new Configuration();
            }
            System.out.println("Loading configuration from: " + configFileLocation);
            return yaml.loadAs(in, Configuration.class);
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
            return new Configuration();
        }
    }


    public static Configuration getConfiguration() {
        return getConfiguration(TestConstants.DEFAULT_CONFIG_FILE_PATH);
    }


}
