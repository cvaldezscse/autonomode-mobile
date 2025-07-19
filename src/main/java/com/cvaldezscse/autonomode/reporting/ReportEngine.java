package com.cvaldezscse.autonomode.reporting;

import com.cvaldezscse.autonomode.reporting.model.TestPlan;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.openqa.selenium.remote.http.Routable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class ReportEngine {

    private String template;
    private String html;

    public ReportEngine(String template, String html) {
        this.template = template;
        this.html = html;
    }

    public void generateReport(TestPlan testPlan) {
        // Ensure report directory exists
        File reportDir = new File(REPORT_DIR);
        if (!reportDir.exists())
            reportDir.mkdirs();

        // sets, pass, fail, total, rates
        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle:        */

        /* Create and adjust the configuration singleton */
        Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_27);
        try {
            // Find the templates directory
            String templatesDir = determineTemplatesDirectory();
            if (templatesDir == null) {
                System.out.println("Unable to locate templates directory");
                throw new IOException("Unable to locate templates directory");
            }
            cfg.setDirectoryForTemplateLoading(new File(templatesDir));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(true);
            cfg.setWrapUncheckedExceptions(true);
            System.out.println("Freemarker configuration set up successfully for directory: " + templatesDir);
        } catch (IOException e) {
            System.out.println(e.getMessage() + e);
            throw new RuntimeException("Unable to setup freemarker config", e);
        }

        /* ------------------------------------------------------------------------ */
        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */

        /* Create a data-model */
        Map<String, Object> root = new HashMap<>();


        if (SHOW_FAILED_TESTS_FIRST) {
            TestPlan orderedTestPlan = new TestPlan();
            orderedTestPlan.setName(testPlan.getName());
            orderedTestPlan.setRepo(testPlan.getRepo());
            orderedTestPlan.setInfo(testPlan.getInfo());
            orderedTestPlan.setDate(testPlan.getDate());
            orderedTestPlan.setVersion(testPlan.getVersion());
            orderedTestPlan.setBuild(testPlan.getBuild());
            orderedTestPlan.setExecutionTime(testPlan.getExecutionTime());
            orderedTestPlan.setPass(testPlan.getPass());
            orderedTestPlan.setPassRate(testPlan.getPassRate());
            orderedTestPlan.setFailRate(testPlan.getFailRate());
            orderedTestPlan.setFail(testPlan.getFail());
            orderedTestPlan.setPlatform(testPlan.getPlatform());
            orderedTestPlan.setAppVersion(testPlan.getAppVersion());
            orderedTestPlan.setAppBuild(testPlan.getAppBuild());
            orderedTestPlan.setTests(testPlan.getTests(true));
            root.put("testplan", orderedTestPlan);
        } else
            root.put("testplan", testPlan);
        root.put("repoUrl", PROJECT_REPO_URL);
        root.put("environment", ENVIRONMENT);
        String finalFormatPlatform = (IS_LOCAL) ? "%s -> %s" : STRING_WILDCARD;
        root.put("platform", format(finalFormatPlatform, PLATFORM().name(), DEVICE_GENERAL_NAME));
        root.put("appVersionPlusBuild", APP_VERSION_AND_BUILD);
        root.put("markupTitle", REPORT_MARKUP_TITLE);

        /* Get the template (uses cache internally) */
        Template temp = null;
        try {
            // Copy the image to the report directory if it exists
            copyResourceToDirectory(IRHYTHM_IMG, REPORT_DIR);
            temp = cfg.getTemplate(template);
            File file = new File(REPORT_DIR, html);
            Writer writer = new FileWriter(file);
            temp.process(root, writer);
            writer.close();
            System.out.println("Test Report Created at: " + file.getAbsolutePath());
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + ex);
            throw new RuntimeException("Unable to generate report", ex);
        }
    }

    private void copyResourceToDirectory(String resourceName, String targetDir) {
        try {
            // Try to load the resource
            InputStream resourceStream = ResourceLoader.getResourceAsStream(RESOURCES_DIR + "/" + resourceName);
            if (resourceStream == null)
                resourceStream = ResourceLoader.getResourceAsStream(resourceName);

            if (resourceStream != null) {
                // Ensure target directory exists
                File directory = new File(targetDir);
                if (!directory.exists())
                    directory.mkdirs();

                // Copy the resource
                Path targetPath = Paths.get(targetDir, resourceName);
                Files.copy(resourceStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Resource copied successfully: " + resourceName + " to " + targetPath);
            } else {
                System.out.println("Could not find resource to copy: " + resourceName);

                // In Device Farm, create a dummy image file if needed
                if (IS_DEVICEFARM_RUN && resourceName.endsWith(".png")) {
                    File dummyFile = new File(targetDir, resourceName);
                    dummyFile.createNewFile();
                    System.out.println("Created empty dummy file: " + dummyFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to copy resource: " + resourceName + " - " + e.getMessage());
        }
    }

    private String determineTemplatesDirectory() {
        // First try using ResourceLoader
        String resourcePath = ResourceLoader.getResourcePath(RESOURCES_DIR);
        if (resourcePath != null)
            return resourcePath;

        // For Device Farm, try current directory
        if (IS_DEVICEFARM_RUN) {
            File currentDir = new File(".");
            System.out.println("Using current directory for templates: " + currentDir.getAbsolutePath());
            return currentDir.getAbsolutePath();
        }

        // Try to create and use a temp directory as last resort
        try {
            Path tempDir = Files.createTempDirectory("freemarker-templates");
            // Copy template to temp directory
            InputStream templateStream = ResourceLoader.getResourceAsStream(template);
            if (templateStream != null) {
                Files.copy(templateStream, tempDir.resolve(template), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Created temporary templates directory: " + tempDir);
                return tempDir.toString();
            }
        } catch (IOException e) {
            System.out.println("Failed to create temp directory for templates: " + e.getMessage());
        }

        // If all else fails, try using "."
        return ".";
    }

}
