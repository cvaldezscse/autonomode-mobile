package com.cvaldezscse.autonomode.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TestSource annotation provides a unified way to specify test data sources.
 * This annotation can work with both any and JSON data sources based on configuration.
 *
 * @author Carlos Valdez
 * @version 1.0
 * @since Jun/2025
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestSource {
    /**
     * Test identifier that matches either a Jama Global ID or a JSON key
     *
     * @return The test identifier
     */
    String id();

    /**
     * Source type to use for test data
     *
     * @return The source type (JAMA, JSON, or AUTO)
     */
    SourceType source() default SourceType.AUTO;

    /**
     * Enumeration of possible test data source types
     */
    enum SourceType {
        /**
         * Use Jama as the test data source
         */
        JAMA,

        /**
         * Use JSON as the test data source
         */
        JSON,

        /**
         * Automatically determine based on configuration
         */
        AUTO
    }
}