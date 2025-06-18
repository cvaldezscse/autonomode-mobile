package com.cvaldezscse.autonomode.core.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestPlan {
    private static final Logger logger = LoggerFactory.getLogger(TestPlan.class);

    private String name;
    private String repo;
    private String info;
    private String date;
    private String version;
    private String build;
    private String executionTime;
    private String pass;
    private String passRate;
    private String failRate;
    private String fail;
    private String platform;
    private String appVersion;
    private String appBuild;
    private String markupTitle;
    private Map<String, TestCase> tests;

    public TestPlan() {
        tests = new HashMap<>();
        this.date = new SimpleDateFormat("MM/dd/YYYY hh:mm:ss aaa").format(new Date());
    }

    public TestCase getTestByGlobalId(String globalId) {
        TestCase test = null;
        for (String name : tests.keySet()) {
            test = tests.get(name);
            if (test.getGlobalId().equalsIgnoreCase(globalId))
                break;
        }
        return test;
    }

    public void addTest(TestCase test) {
        tests.put(test.getName(), test);
        logger.info("added test: " + test);
    }

    public void setStats() {
        int passCount = 0;
        int failCount = 0;
        for (TestCase testCase : tests.values()) {
            logger.info("Set stats entry: {}" + testCase.toString(), logger);
            if (testCase.getColor().equalsIgnoreCase("9ACD32"))
                passCount++;
            else
                failCount++;
        }
        setPass(String.valueOf(passCount));
        setFail(String.valueOf(failCount));
        int total = tests.size();
        if (total > 0) {
            BigDecimal passRate = BigDecimal.valueOf((double) passCount / total * 100D).setScale(2, RoundingMode.UP);
            BigDecimal failRate = BigDecimal.valueOf((double) failCount / total * 100D).setScale(2, RoundingMode.UP);
            setPassRate(passRate.toString());
            setFailRate(failRate.toString());
        } else {
            setPassRate("0.00");
            setFailRate("0.00");
        }
        logInfo("SET STATS COMPLETE", logger);
    }

    /**
     * Returns the tests map, optionally sorted according to specified criteria.
     *
     * @param sortFailedFirst If true, failed tests appear before passed tests
     * @return Map of tests, potentially reordered according to parameters
     */
    public Map<String, TestCase> getTests(boolean sortFailedFirst) {
        if (!sortFailedFirst)
            return tests; // Return original order (as soon as the test case is executed)

        // Create a LinkedHashMap to maintain insertion order
        Map<String, TestCase> orderedTests = new LinkedHashMap<>();

        // Constants for pass/fail colors
        final String PASS_COLOR = TestConstants.REPORT_CONSTANT_GREEN_HEX_COLOR; // Green color hex
        final String FAIL_COLOR = TestConstants.REPORT_CONSTANT_RED_HEX_COLOR; // Red color hex

        // Add all failed tests firstly
        tests.entrySet().stream()
                .filter(entry -> entry.getValue().getColor() != null &&
                        entry.getValue().getColor().equalsIgnoreCase(FAIL_COLOR))
                .forEach(entry -> orderedTests.put(entry.getKey(), entry.getValue()));

        // Adding all passed tests secondly
        tests.entrySet().stream()
                .filter(entry -> entry.getValue().getColor() == null ||
                        entry.getValue().getColor().equalsIgnoreCase(PASS_COLOR))
                .forEach(entry -> orderedTests.put(entry.getKey(), entry.getValue()));

        return orderedTests;
    }

    /**
     * Original getter to maintain compatibility
     */
    public Map<String, TestCase> getTests() {
        return getTests(false);
    }


}
