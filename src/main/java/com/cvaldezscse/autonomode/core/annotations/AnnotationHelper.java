package com.cvaldezscse.autonomode.core.annotations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AnnotationHelper {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHelper.class);

    /**
     * Determines the appropriate source type based on annotation and configuration.
     *
     * @param source      TestSource annotation
     * @param jamaEnabled Whether Jama is enabled in configuration
     * @return The determined SourceType
     */
    public static TestSource.SourceType determineSourceType(TestSource source, boolean jamaEnabled) {
        if (source.source() != TestSource.SourceType.AUTO)
            return source.source();
        return jamaEnabled ? TestSource.SourceType.JAMA : TestSource.SourceType.JSON;
    }


    /**
     * Gets the TestSource annotation from a class.
     *
     * @param c Class to check for TestSource annotation
     * @return The TestSource annotation if found
     * @throws RuntimeException if the annotation is not present
     */
    public static TestSource getTestSource(Class<?> c) {
        if (!c.isAnnotationPresent(TestSource.class))
            throw new RuntimeException("Missing TestSource annotation for class: " + c.getSimpleName());
        return c.getAnnotation(TestSource.class);
    }


    /**
     * Creates a TestCase from the TestSource annotation.
     *
     * @param c           Class with TestSource annotation
     * @param jamaEnabled Whether Jama is enabled in configuration
     * @return A TestCase object populated with data from the appropriate source
     * @throws RuntimeException if test data cannot be loaded
     */
//    public static TestCase createTestCaseFromSource(Class<?> c, boolean jamaEnabled) {
//        TestSource testSource = getTestSource(c);
//        TestSource.SourceType sourceType = determineSourceType(testSource, jamaEnabled);
//
//        if (sourceType == TestSource.SourceType.JAMA) {
//            JamaTest jamaTest = new JamaTestAdapter(testSource.id());
//            try {
//                return JamaClient.createTestCaseFromJamaDetails(jamaTest);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to create test case from Jama", e);
//            }
//        } else {
//            JsonTest jsonTest = new JsonTestAdapter(testSource.id());
//            return JsonHelper.createTestCaseFromJsonDetails(jsonTest);
//        }
//    }

    /**
     * Gets the TestSourceStep annotation from a method in a class.
     *
     * @param c          Class where the method is defined
     * @param methodName Name of the method to check
     * @return The TestSourceStep annotation if found, null otherwise
     */
//    public static TestSourceStep getTestSourceStep(Class<?> c, String methodName) {
//        for (Method m : c.getDeclaredMethods()) {
//            if (m.getName().equals(methodName) && m.isAnnotationPresent(TestSourceStep.class))
//                return m.getAnnotation(TestSourceStep.class);
//        }
//        return null;
//    }


    /**
     * Defines if the class belongs to a JsonTest
     *
     * @param c Class<?> object provides the information of the JsonTest annotation
     * @return JsonTest annotation
     */
//    public static JsonTest getJsonTest(Class<?> c) {
//        JsonTest jsonTest = null;
//        if (c.isAnnotationPresent(JsonTest.class)) {
//            Annotation annotation = c.getAnnotation(JsonTest.class);
//            jsonTest = (JsonTest) annotation;
//        }
//        if (jsonTest == null)
//            throw new RuntimeException(format("Did not find JsonTest Annotation for class: %s", c.getSimpleName()));
//        return jsonTest;
//    }

    /**
     * Defines if the method belongs to a JsonTestStep
     *
     * @param c          Class<?> object provides the information of the JsonTest annotation
     * @param methodName String name of the method currently in execution
     * @return JsonTestStep annotation
     */
//    public static JsonTestStep getJsonTestStep(Class<?> c, String methodName) {
//        JsonTestStep jsonTestStep = null;
//        for (Method m : c.getDeclaredMethods()) {
//            if (m.getName().equals(methodName) && m.isAnnotationPresent(JsonTestStep.class)) {
//                Annotation annotation = m.getAnnotation(JsonTestStep.class);
//                jsonTestStep = (JsonTestStep) annotation;
//                break;
//            }
//        }
//        return jsonTestStep;
//    }


//    public static JamaTest getJamaTest(Class<?> c) {
//        JamaTest jamaTest = null;
//        if (c.isAnnotationPresent(JamaTest.class))
//            jamaTest = c.getAnnotation(JamaTest.class);
//        if (jamaTest == null)
//            throw new RuntimeException(format(EXCEPTION_CLASS_DOESNT_HAVE_JAMATEST_ANNOTATION, c.getSimpleName()));
//        return jamaTest;
//    }

//    public static JamaTestStep getJamaTestStep(Class<?> c, String methodName) {
//        JamaTestStep jamaTestStep = null;
//        for (Method m : c.getDeclaredMethods()) {
//            if (m.getName().equals(methodName) && m.isAnnotationPresent(JamaTestStep.class)) {
//                jamaTestStep = m.getAnnotation(JamaTestStep.class);
//                break;
//            }
//        }
//        return jamaTestStep;
//    }


    /**
     * Initializes the results of the test steps
     * Sets default values for results and colors if they are not already set.
     *
     * @param testCase The test case whose steps will be initialized
     */
//    private static void initializeStepResults(TestCase testCase) {
//        try {
//            Method getStepsMethod = TestCase.class.getMethod("getSteps");
//            Object stepsObject = getStepsMethod.invoke(testCase);
//
//            if (stepsObject instanceof Map) {
//                @SuppressWarnings("unchecked")
//                Map<String, Object> steps = (Map<String, Object>) stepsObject;
//
//                for (Map.Entry<String, Object> entry : steps.entrySet()) {
//                    if (entry.getValue() instanceof TestExecution) {
//                        TestExecution execution = (TestExecution) entry.getValue();
//                        if (execution.getResult() == null)
//                            execution.setResult(REPORT_CONSTANT_PASS_TEXT);
//                        if (execution.getColor() == null)
//                            execution.setColor(REPORT_CONSTANT_GREEN_HEX_COLOR);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logError("Error initializing step results: " + e.getMessage(), logger);
//        }
//    }

    /**
     * JamaTest implementation adapter for TestSource.
     */
//    private static class JamaTestAdapter implements JamaTest {
//        private final String value;
//
//        public JamaTestAdapter(String value) {
//            this.value = value;
//        }
//
//        @Override
//        public String value() {
//            return value;
//        }
//
//        @Override
//        public Class<? extends Annotation> annotationType() {
//            return JamaTest.class;
//        }
//    }

    /**
     * JsonTest implementation adapter for TestSource.
     */
//    private static class JsonTestAdapter implements JsonTest {
//        private final String value;
//
//        public JsonTestAdapter(String value) {
//            this.value = value;
//        }
//
//        @Override
//        public String value() {
//            return value;
//        }
//
//        @Override
//        public Class<? extends Annotation> annotationType() {
//            return JsonTest.class;
//        }
//    }
}
