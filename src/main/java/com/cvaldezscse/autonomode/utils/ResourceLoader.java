package com.cvaldezscse.autonomode.utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceLoader {

    public static InputStream getResourceAsStream(String resourcePath) {
        logInfo("Attempting to load resource: " + resourcePath, logger);

        // Try class loader approach first (works when resources are in classpath)
        InputStream stream = ResourceLoader.class.getClassLoader().getResourceAsStream(resourcePath);
        if (stream != null) {
            logInfo("Resource loaded via ClassLoader", logger);
            return stream;
        }

        // Try with absolute path for resources at root in Device Farm
        try {
            File file = new File(resourcePath);
            if (file.exists()) {
                logInfo("Resource loaded via absolute path: " + file.getAbsolutePath(), logger);
                return Files.newInputStream(file.toPath());
            }
        } catch (Exception e) {
            logWarn("Could not load resource from absolute path: " + e.getMessage(), logger);
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
                    logInfo("Resource loaded via relative path: " + filePath.toAbsolutePath(), logger);
                    return Files.newInputStream(filePath);
                }
            } catch (Exception e) {
                logError("Failed to load from " + path + ": " + e.getMessage(), logger);
            }
        }

        // Try as a URL as a last resort
        try {
            URL url = new URL(resourcePath);
            logInfo("Resource loaded via URL", logger);
            return url.openStream();
        } catch (Exception e) {
            logError("Not a valid URL: " + e.getMessage(), logger);
        }
        logError("Failed to load resource: " + resourcePath, logger);
        return null;
    }

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
                logError("Failed to find path " + path + ": " + e.getMessage(), logger);
            }
        }
        // Try using classpath
        URL url = ResourceLoader.class.getClassLoader().getResource(resourcePath);
        if (url != null)
            return url.getPath();
        logError("Failed to locate resource path: " + resourcePath, logger);
        return null;
    }
}
