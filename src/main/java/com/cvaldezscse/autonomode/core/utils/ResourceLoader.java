package com.cvaldezscse.autonomode.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceLoader {
    private static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);

    /**
     * Gets a resource as an InputStream, trying multiple strategies to locate it.
     * This method is designed to work in both local and Device Farm environments.
     *
     * @param resourcePath The path to the resource (e.g., "configuration.yaml")
     * @return InputStream of the resource, or null if not found
     */
    public static InputStream getResourceAsStream(String resourcePath) {
        logger.info("Attempting to load resource: " + resourcePath, logger);

        // Try class loader approach first (works when resources are in classpath)
        InputStream stream = ResourceLoader.class.getClassLoader().getResourceAsStream(resourcePath);
        if (stream != null) {
            logger.info("Resource loaded via ClassLoader", logger);
            return stream;
        }

        // Try with absolute path for resources at root in Device Farm
        try {
            File file = new File(resourcePath);
            if (file.exists()) {
                logger.info("Resource loaded via absolute path: " + file.getAbsolutePath(), logger);
                return Files.newInputStream(file.toPath());
            }
        } catch (Exception e) {
            logger.warn("Could not load resource from absolute path: " + e.getMessage(), logger);
        }

        // Try with relative paths
        String[] possiblePaths = {
                resourcePath,
                "src/main/resources/" + resourcePath,
                "src/test/resources/" + resourcePath,
                "../" + resourcePath,
                "./resources/" + resourcePath,
                "./config/" + resourcePath
        };

        for (String path : possiblePaths) {
            try {
                Path filePath = Paths.get(path);
                if (Files.exists(filePath)) {
                    logger.info("Resource loaded via relative path: " + filePath.toAbsolutePath(), logger);
                    return Files.newInputStream(filePath);
                }
            } catch (Exception e) {
                logger.error("Failed to load from " + path + ": " + e.getMessage(), logger);
            }
        }

        // Try as a URL as a last resort
        try {
            URL url = new URL(resourcePath);
            logger.info("Resource loaded via URL", logger);
            return url.openStream();
        } catch (Exception e) {
            logger.error("Not a valid URL: " + e.getMessage(), logger);
        }
        logger.error("Failed to load resource: " + resourcePath, logger);
        return null;
    }

    /**
     * Gets the absolute path to a resource file, trying multiple strategies to locate it.
     *
     * @param resourcePath The path to the resource
     * @return The absolute path to the resource, or null if not found
     */
    public static String getResourcePath(String resourcePath) {
        // Try with absolute path
        File file = new File(resourcePath);
        if (file.exists())
            return file.getAbsolutePath();
        // Try with relative paths
        String[] possiblePaths = {
                resourcePath,
                "src/main/resources/" + resourcePath,
                "src/test/resources/" + resourcePath,
                "../" + resourcePath,
                "./resources/" + resourcePath,
                "./config/" + resourcePath
        };
        for (String path : possiblePaths) {
            try {
                Path filePath = Paths.get(path);
                if (Files.exists(filePath))
                    return filePath.toAbsolutePath().toString();
            } catch (Exception e) {
                logger.error("Failed to find path " + path + ": " + e.getMessage(), logger);
            }
        }
        // Try using classpath
        URL url = ResourceLoader.class.getClassLoader().getResource(resourcePath);
        if (url != null)
            return url.getPath();
        logger.error("Failed to locate resource path: " + resourcePath, logger);
        return null;
    }
}
