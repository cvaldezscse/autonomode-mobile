package com.cvaldezscse.autonomode.core.config;

import com.cvaldezscse.autonomode.core.utils.ResourceLoader;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

@Getter
@Setter
public class Configuration {
    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

    private String platformToRun;
    private String executionType;
    private String testSource;

    private String assemblyZipFile;
    private String sauceApiKey;
    private String sauceSecret;
    private String sauceURL;
    private String saucePort;

    private String environment;

    private String reportMarkupTitle;
    private String projectRepositoryUrl;
    private String htmlReportFileName;
    private String androidAppVersion;
    private String androidAppBuild;
    private String iosAppVersion;
    private String iosAppBuild;

    private String iosDeviceName;
    private String iosPlatformVersion;
    private String iosDeviceUID;
    private String iosAppFileName;
    private String iosBundleId;
    private String iosDefaultFontSetting;
    private String iosXcodeOrgId;
    private String iosXcodeSigningId;

    /**
     * Gets the configuration info placed on a Configuration objects
     *
     * @param configFileLocation String location of the Configuration file
     * @return Configuration Object that has all the information from the Config file
     */
    public static Configuration getConfiguration(String configFileLocation) {
        Configuration config = null;
        Yaml yaml = new Yaml();
        // Extract just the filename if a full path is provided
        String fileName = configFileLocation;
        if (fileName.contains("/"))
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        try {
            // First try the original path
            InputStream in = ResourceLoader.getResourceAsStream(configFileLocation);

            // If not found, try with just the filename
            if (in == null) {
                in = ResourceLoader.getResourceAsStream(fileName);
                logger.info("Trying to load configuration from: " + fileName, logger);
            }

            if (in == null) {
                logger.error("Unable to load configuration from: " + configFileLocation, logger);
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

    /**
     * Returns the HTML report file name.
     * If the file name is empty, returns the default report file name.
     * Ensures the file name has the correct HTML extension.
     *
     * @return the validated HTML report file name
     */
    public String getHtmlReportFileName() {
        if (htmlReportFileName.isEmpty())
            return DEFAULT_HTML_REPORT_FILENAME;
        return htmlReportFileName.endsWith(DEFAULT_HTML_FILE_EXTENSION)
                ? htmlReportFileName
                : htmlReportFileName + DEFAULT_HTML_FILE_EXTENSION;
    }

    /**
     * Gets the report markup title or returns the default if not specified.
     *
     * @return The report markup title with platform information
     */
    public String getReportMarkupTitle() {
        return reportMarkupTitle != null && !reportMarkupTitle.isEmpty()
                ? reportMarkupTitle
                : String.format("%s (Local) Test Report", PLATFORM().name());
    }



}
