package com.cvaldezscse.autonomode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Application started");
        logger.debug("Debug message with value: {}", 42);
        logger.warn("This is a warning");
        logger.error("Something went wrong!", new RuntimeException("Simulated error"));
    }
}
