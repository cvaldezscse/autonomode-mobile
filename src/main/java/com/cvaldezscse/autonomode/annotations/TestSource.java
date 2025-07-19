package com.cvaldezscse.autonomode.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestSource {

    String id();
    SourceType source() default SourceType.AUTO;

    /**
     * Enumeration of possible test data source types
     */
    enum SourceType {
        JSON,
        JAMA,
        AUTO
    }
}