package com.cvaldezscse.autonomode.reporting.model;

import com.cvaldezscse.autonomode.constants.TestConstants;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class TestPlan {
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
            if (test.getTestCaseId().equalsIgnoreCase(globalId))
                break;
        }
        return test;
    }

    public void addTest(TestCase test) {
        tests.put(test.getName(), test);
        System.out.println("added test: " + test);
    }

    public void setStats() {
        int passCount = 0;
        int failCount = 0;
        for (TestCase testCase : tests.values()) {
            System.out.println("Set stats entry: {}" + testCase.toString());
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
        System.out.println("SET STATS COMPLETE");
    }

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

    public Map<String, TestCase> getTests() {
        return getTests(false);
    }




}
