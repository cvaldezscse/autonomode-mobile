package com.cvaldezscse.autonomode.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TestSourceStep annotation identifies test methods as specific steps in a test case.
 * Works with the TestSource annotation to provide a unified approach for test steps.
 * @author Carlos Valdez
 * @version 1.0
 * @since Jun/2025
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestSourceStep {
    /**
     * Step identifier that matches a step number in the test case
     *
     * @return The step identifier
     */
    String value();
}