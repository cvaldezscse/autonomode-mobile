package com.cvaldezscse.autonomode.reporting.listeners;

import com.cvaldezscse.autonomode.annotations.AnnotationHelper;
import com.cvaldezscse.autonomode.annotations.JamaTest;
import com.cvaldezscse.autonomode.annotations.JamaTestStep;
import com.cvaldezscse.autonomode.annotations.JsonTest;
import com.cvaldezscse.autonomode.annotations.JsonTestStep;
import com.cvaldezscse.autonomode.annotations.TestSource;
import com.cvaldezscse.autonomode.annotations.TestSourceStep;
import com.cvaldezscse.autonomode.config.Configuration;
import com.cvaldezscse.autonomode.config.JamaConfiguration;
import com.cvaldezscse.autonomode.reporting.ReportEngine;
import com.cvaldezscse.autonomode.reporting.model.TestCase;
import com.cvaldezscse.autonomode.reporting.model.TestExecution;
import com.cvaldezscse.autonomode.reporting.model.TestPlan;
import com.cvaldezscse.autonomode.reporting.model.TestStep;
import org.testng.IClassListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cvaldezscse.autonomode.constants.TestConstants.BASE_CONFIG;
import static java.lang.String.format;

public class ReportingListener implements IClassListener, ITestListener, ISuiteListener {
    public static final TestPlan testPlan = new TestPlan();
    private long suiteStartTime;
//    private static JamaConfiguration jamaConf = new JamaConfiguration(BASE_CONFIG.getJamaConfiguration());
    private final Map<String, Map<String, String>> testStepResults = new HashMap<>();
    private static int testCount = 0;


    /**
     * Begins test case preparation based on TestSource annotation.
     *
     * @param testClass ITestClass representing the class that is about to be executed
     */
    @Override
    public void onBeforeClass(ITestClass testClass) {
        Class<?> c = testClass.getRealClass();
        logInfo("Starting test preparation for: " + c.getSimpleName(), logger);

        try {
            logInfo("Class has TestSource: " + c.isAnnotationPresent(TestSource.class), logger);
            logInfo("Class has JamaTest: " + c.isAnnotationPresent(JamaTest.class), logger);
            TestCase testCase = null;
            if (c.isAnnotationPresent(TestSource.class)) {
                logInfo("Creating test case from TestSource", logger);
                testCase = AnnotationHelper.createTestCaseFromSource(c, jamaConf.isJamaEnabled());
            } else if (jamaConf.isJamaEnabled() && c.isAnnotationPresent(JamaTest.class)) {
                logInfo("Creating test case from JamaTest", logger);
                onBeforeJamaTest(testClass);
                return;
            } else if (!jamaConf.isJamaEnabled() && c.isAnnotationPresent(JsonTest.class)) {
                logInfo("Creating test case from JsonTest", logger);
                onBeforeJsonTest(testClass);
                return;
            } else
                throw new RuntimeException("No valid test annotation found on class: " + c.getSimpleName());

            if (testCase == null)
                throw new RuntimeException("Failed to create test case for: " + c.getSimpleName());
            logInfo("======================= ->" + testCase.getGlobalId() + "<- =======================", logger);
            logInfo("Adding test case to test plan: " + testCase.getGlobalId(), logger);
            testPlan.addTest(testCase);
        } catch (Exception e) {
            logError("Error in onBeforeClass: " + e.getMessage(), logger);
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * AfterClass method which is not currently used but overriden since it belongs to the IClassListener Interface
     *
     * @param testClass ITestClass Class that has been executed
     */
    /**
     * AfterClass method which is not currently used but overriden since it belongs to the IClassListener Interface
     *
     * @param testClass ITestClass Class that has been executed
     */
    @Override
    public void onAfterClass(ITestClass testClass) {
        logInfo("ReportingClassListener -> onAfterClass method executed", logger);
        if (jamaConf.isUpdateTestRunStatus()) {
            try {
                Class<?> c = testClass.getRealClass();
                String testId = null;
                if (c.isAnnotationPresent(TestSource.class)) {
                    TestSource testSource = c.getAnnotation(TestSource.class);
                    testId = testSource.id();
                } else if (c.isAnnotationPresent(JamaTest.class)) {
                    JamaTest jamaTest = c.getAnnotation(JamaTest.class);
                    testId = jamaTest.value();
                } else if (c.isAnnotationPresent(JsonTest.class)) {
                    JsonTest jsonTest = c.getAnnotation(JsonTest.class);
                    testId = jsonTest.value();
                }
                if (testId != null) {
                    Map<String, String> results = testStepResults.get(testId);
                    if (results != null && !results.isEmpty()) {
                        String testRunId = jamaConf.getTestRun();
                        updateJamaTestRunStatus(testRunId, results);
                        logInfo("Updated Jama test run status for test ID: " + testRunId, logger);
                    } else {
                        logWarn("No test results found for test ID: " + testId, logger);
                    }
                }
            } catch (Exception e) {
                logError("Error updating Jama test run status: " + e.getMessage(), logger);
            }
        }
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        setResultAndColor(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        setResultAndColor(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        setResultAndColor(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        setResultAndColor(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        setResultAndColor(result);
    }

    /**
     * Starts the whole Suite
     *
     * @param suite ISuite object that allows us to start the execution with gathering some info from the config
     *              file, even before the Driver Object generation
     */
    @Override
    public void onStart(ISuite suite) {
        suiteStartTime = System.currentTimeMillis();
        System.out.println(APP_VERSION_AND_BUILD);
        testPlan.setVersion(APP_VERSION_AND_BUILD);
        logger.info(LOG_MSG_TEST_STARTED);
    }

    /**
     * Sets the result and color for a test execution, supporting both legacy and new annotations.
     * Also collects results for Jama status updates if enabled.
     *
     * @param result ITestResult Result of the execution
     */
    public void setResultAndColor(ITestResult result) {
        Class<?> c = result.getMethod().getRealClass();
        String resultAsString;
        String color;

        // Determine result status
        switch (result.getStatus()) {
            case 1:
                resultAsString = REPORT_CONSTANT_PASS_TEXT;
                color = REPORT_CONSTANT_GREEN_HEX_COLOR;
                break;
            default:
                resultAsString = REPORT_CONSTANT_FAIL_TEXT;
                color = REPORT_CONSTANT_RED_HEX_COLOR;
        }

        String methodName = result.getMethod().getMethodName();
        String testId = null;
        String stepId = null;

        // Try new unified annotation first
        if (c.isAnnotationPresent(TestSource.class)) {
            TestSource testSource = AnnotationHelper.getTestSource(c);
            TestSourceStep testSourceStep = AnnotationHelper.getTestSourceStep(c, methodName);
            testId = testSource.id();
            stepId = testSourceStep != null ? testSourceStep.value() : "1";
            processTestSourceResult(c, methodName, resultAsString, color);
        }
        // Fall back to legacy annotations
        else if (jamaConf.isJamaEnabled() && c.isAnnotationPresent(JamaTest.class)) {
            JamaTest jamaTest = AnnotationHelper.getJamaTest(c);
            JamaTestStep jamaTestStep = AnnotationHelper.getJamaTestStep(c, methodName);
            testId = jamaTest.value();
            stepId = jamaTestStep != null ? jamaTestStep.value() : "1";
            processJamaTestResult(c, methodName, resultAsString, color);
        } else if (!jamaConf.isJamaEnabled() && c.isAnnotationPresent(JsonTest.class)) {
            JsonTest jsonTest = AnnotationHelper.getJsonTest(c);
            JsonTestStep jsonTestStep = AnnotationHelper.getJsonTestStep(c, methodName);
            testId = jsonTest.value();
            stepId = jsonTestStep != null ? jsonTestStep.value() : "1";
            processJsonTestResult(c, methodName, resultAsString, color);
        }

        if (jamaConf.isUpdateTestRunStatus() && testId != null && stepId != null) {
            testStepResults.computeIfAbsent(testId, k -> new HashMap<>());
            testStepResults.get(testId).put(stepId, resultAsString);
        }
    }


    /**
     * Process test results for classes using the TestSource annotation.
     */
    private void processTestSourceResult(Class<?> c, String methodName, String resultAsString, String color) {
        try {
            TestSource testSource = AnnotationHelper.getTestSource(c);
            TestSourceStep testSourceStep = AnnotationHelper.getTestSourceStep(c, methodName);
            TestCase test = testPlan.getTestByGlobalId(testSource.id());
            if (test == null)
                throw new RuntimeException(format(EXCEPTION_NOT_FOUND_TEST, testSource.id()));
            if (test.getColor() == null || !test.getColor().equalsIgnoreCase(REPORT_CONSTANT_RED_HEX_COLOR))
                test.setColor(color);
            if (testSourceStep != null) {
                TestExecution testStep = test.getSteps().get(testSourceStep.value());
                if (testStep != null) {
                    testStep.setResult(resultAsString);
                    testStep.setColor(color);
                } else
                    logWarn("No test step found for ID: " + testSourceStep.value(), logger);
            }
        } catch (Exception e) {
            logError("Error processing TestSource result: " + e.getMessage(), logger);
        }
    }

    /**
     * Publishes test results to the DynamoDB database for the SQA Dashboard.
     * This method only executes if the build is not a local build (BAMBOO_BUILD_ID is not "Local").
     * It retrieves test results from the `testPlan` object, formats them, and then uses the `AwsDynamoDb`
     * class to push the data to the DynamoDB table.  It also includes the build key, bamboo build ID,
     * pass rate, passed and failed test cases, the current date and time, and the app version.
     *
     * @throws Exception If an error occurs during the process of pushing test results to DynamoDB.
     *                   This exception is caught and logged, but not re-thrown.
     */
    private void pushTestResultsToDynamo() {
        if (!BAMBOO_BUILD_ID.equalsIgnoreCase("Local")) {
            try {
                AwsDynamoDb awsDynamoDb = new AwsDynamoDb();
                Integer passedTestCases = Integer.valueOf(testPlan.getPass());
                Integer failedTestCases = Integer.valueOf(testPlan.getFail());
                Double passRate = Double.valueOf(testPlan.getPassRate());
                String date = testPlan.getDate();
                String version = isAndroid() ? ANDROID_APP_VERSION : IOS_APP_VERSION;
                awsDynamoDb.pushTestResultsToDynamoDB(BUILD_KEY, BAMBOO_BUILD_ID, passRate, passedTestCases, failedTestCases, date, version);
                logInfo("Results published in the SQA Dashboard", logger);
            } catch (Exception e) {
                logError(format("Error pushing test results to the SQA Dashboard: {%s}", e.getMessage()), logger);
            }
        } else
            logInfo("Local execution, no results will be published in the SQA Dashboard", logger);
    }


    /**
     * Updates the Jama test run status, ensuring result count matches test case steps exactly.
     *
     * @param testRunId The Jama test run ID
     * @param results   Map of step IDs to results (e.g., "1" -> "PASS", "2" -> "FAIL")
     */
    private void updateJamaTestRunStatus(String testRunId, Map<String, String> results) {
        try {
            if (testRunId == null || testRunId.isEmpty()) {
                logWarn("Cannot update Jama status: No test run ID provided in configuration", logger);
                return;
            }
            GetGlobalIdResponse testObject = getTestCase(testRunId, jamaConf.getJamaProject());
            int totalSteps = testObject.getTestCaseSteps().size();
            if (totalSteps == 0) {
                logWarn("Jama test case has no steps. Skipping update.", logger);
                return;
            }
            ArrayList<String> formattedResults = new ArrayList<>();
            for (int i = 1; i <= totalSteps; i++) {
                String stepId = String.valueOf(i);
                String result = results.getOrDefault(stepId, REPORT_CONSTANT_PASS_TEXT);  // o usar "NOT_EXECUTED"
                formattedResults.add(result.equals(REPORT_CONSTANT_PASS_TEXT) ? "PASSED" : "FAILED");
            }
            changeTestRunStatus(testRunId, formattedResults);
            testCount++;
            logInfo(">>>>> ✅ Jama status updated successfully for testRunId: " + testRunId, logger);

        } catch (Exception e) {
            logError(">>>>> ❌ Error updating Jama test run status: " + e.getMessage(), logger);
        }
    }


//    /**
//     * Updates the test run status in Jama using the test results collected during test execution.
//     *
//     * @param testRunId The Jama test run ID to update
//     * @param results   Map of step IDs to their results (PASS/FAIL)
//     */
//    private void updateJamaTestRunStatus(String testRunId, Map<String, String> results) {
//        try {
//            if (testRunId == null || testRunId.isEmpty()) {
//                logWarn("Cannot update Jama status: No test run ID provided in configuration", logger);
//                return;
//            }
//            ArrayList<String> formattedResults = new ArrayList<>();
//            for (int i = 1; i <= results.size(); i++) {
//                String stepId = String.valueOf(i);
//                String result = results.getOrDefault(stepId, REPORT_CONSTANT_PASS_TEXT);
//                String jamaResult;
//                if (result.equals(REPORT_CONSTANT_PASS_TEXT))
//                    jamaResult = "PASSED";
//                else
//                    jamaResult = "FAILED";
//                formattedResults.add(jamaResult);
//            }
//            changeTestRunStatus(testRunId, formattedResults);
//            testCount++;
//            logInfo("Successfully updated test run status in Jama for run ID: " + testRunId, logger);
//        } catch (Exception e) {
//            logError("Error updating Jama test run status: " + e.getMessage(), logger);
//        }
//    }


    /**
     * Get test case data from jama
     *
     * @param testClass Testclass with test case information
     */
    public void onBeforeJamaTest(ITestClass testClass) {
        Class<?> c = testClass.getRealClass();
        logInfo("Jama Test Case Start: " + c.getSimpleName(), logger);
        JamaTest jamaTest = AnnotationHelper.getJamaTest(c);
        GetGlobalIdResponse testObject = null;
        testObject = getTestCase(jamaTest.value(), jamaConf.getJamaProject());
        TestCase testCase = new TestCase();
        if (testObject != null) {
            testCase.setName(testObject.getName());
            testCase.setGlobalId(testObject.getGlobalId());
            testCase.setDescription(testObject.getDescription());
            String[] values = null;
            for (int i = 0; i < testObject.getTestCaseSteps().size(); i++) {
                TestStep step = new TestStep();
                values = testObject.getTestCaseSteps().get(i).toString().split(", expectedResult=");
                step.setAction(values[0].replace("{action=", ""));
                step.setExpected(values[1].replace(", notes=}", ""));
                testCase.addSteps(step);
            }
            String url = format(JAMA_URL, testObject.getId(), jamaConf.getJamaProject());
            testCase.setUrl(url);
        }
        if (testCase == null) {
            logError("No test was found in jama for " + jamaTest.value(), logger);
            throw new RuntimeException("Not test found for " + jamaTest.value());
        }
        testPlan.addTest(testCase);
    }

    /**
     * Prepares the test case from JSON data before the test execution.
     *
     * @param testClass ITestClass for the test being executed
     */
    public void onBeforeJsonTest(ITestClass testClass) {
        Class<?> c = testClass.getRealClass();
        logInfo("CLASS WITH JSON STARTED " + c.getName(), logger);
        JsonTest jsonTest = AnnotationHelper.getJsonTest(c);
        TestCase testCase = JsonHelper.createTestCaseFromJsonDetails(jsonTest);
        if (testCase == null) {
            logError("No test found for " + jsonTest.value(), logger);
            throw new RuntimeException("Not test found for " + jsonTest.value());
        }
        testPlan.addTest(testCase);
    }

    /**
     * Process test results for classes using JsonTest annotation.
     *
     * @param c              Class being tested
     * @param methodName     Method name that was executed
     * @param resultAsString String representation of the test result (PASS/FAIL)
     * @param color          Color to use for reporting this result
     */
    private void processJsonTestResult(Class<?> c, String methodName, String resultAsString, String color) {
        try {
            JsonTest jsonTest = AnnotationHelper.getJsonTest(c);
            JsonTestStep jsonTestStep = AnnotationHelper.getJsonTestStep(c, methodName);
            TestCase test = testPlan.getTestByGlobalId(jsonTest.value());
            if (test == null)
                throw new RuntimeException(format(EXCEPTION_NOT_FOUND_TEST, jsonTest.value()));
            if (test.getColor() == null || !test.getColor().equalsIgnoreCase(REPORT_CONSTANT_RED_HEX_COLOR))
                test.setColor(color);

            // Use step index or value
            String stepId = jsonTestStep != null ? jsonTestStep.value() : "1";
            TestExecution testStep = test.getSteps().get(stepId);
            if (testStep != null) {
                testStep.setResult(resultAsString);
                testStep.setColor(color);
            } else
                logWarn("No test step found for ID: " + stepId + " in test: " + jsonTest.value(), logger);
        } catch (Exception e) {
            logError("Error processing JSON test result: " + e.getMessage(), logger);
        }
    }

    /**
     * Process test results for classes using JamaTest annotation.
     *
     * @param c              Class being tested
     * @param methodName     Method name that was executed
     * @param resultAsString String representation of the test result (PASS/FAIL)
     * @param color          Color to use for reporting this result
     */
    private void processJamaTestResult(Class<?> c, String methodName, String resultAsString, String color) {
        try {
            JamaTest jamaTest = AnnotationHelper.getJamaTest(c);
            JamaTestStep jamaTestStep = AnnotationHelper.getJamaTestStep(c, methodName);
            TestCase test = testPlan.getTestByGlobalId(jamaTest.value());
            if (test == null)
                throw new RuntimeException(format(EXCEPTION_NOT_FOUND_TEST, jamaTest.value()));
            if (test.getColor() == null || !test.getColor().equalsIgnoreCase(REPORT_CONSTANT_RED_HEX_COLOR))
                test.setColor(color);
            if (jamaTestStep != null) {
                TestExecution testStep = test.getSteps().get(jamaTestStep.value());
                if (testStep != null) {
                    testStep.setResult(resultAsString);
                    testStep.setColor(color);
                } else
                    logWarn("No test step found for ID: " + jamaTestStep.value(), logger);
            }
        } catch (Exception e) {
            logError("Error processing Jama test result: " + e.getMessage(), logger);
        }
    }

    /**
     * Fills the report on its last format one step behing on filling out the HTML file one test case at a time
     */
    @Override
    public void onFinish(ISuite suite) {
        long executionTime = System.currentTimeMillis() - suiteStartTime;
        testPlan.setExecutionTime(TestUtils.formatExecutionTime(executionTime));
        testPlan.setStats();
        ReportEngine reportEngine = new ReportEngine(REPORT_TEMPLATE_FILENAME, OUTPUT_HTML_REPORT_FILENAME);
        reportEngine.generateReport(testPlan);
        pushTestResultsToDynamo();
    }

}
