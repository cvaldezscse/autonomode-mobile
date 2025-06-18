package com.cvaldezscse.autonomode.core.helpers;

import com.cvaldezscse.autonomode.core.model.TestCase;
import com.cvaldezscse.autonomode.core.model.TestStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class JsonHelper {
    private static final Logger logger = LoggerFactory.getLogger(JsonHelper.class);


    /**
     * Creates a TestCase from JsonTest annotation, compatible with the new TestInfoDetails.json format.
     * This method detects the format based on the presence of test_name or other specific keys.
     *
     * @param jsonTest The JsonTest annotation containing the test ID
     * @return A TestCase object populated with data from the JSON file
     * @throws RuntimeException if the test data cannot be loaded or is invalid
     */
    public static TestCase createTestCaseFromJsonDetails(JsonTest jsonTest) {
        Map<String, Object> testData = JsonReader.getTestData(jsonTest.value());
        TestCase testCase = new TestCase();
        testCase.setName((String) testData.get("test_name"));
        testCase.setDescription((String) testData.get("test_description"));
        testCase.setUrl((String) testData.get("jama_url"));
        testCase.setGlobalId(jsonTest.value());
        List<Map<String, Object>> testStepsArray = (List<Map<String, Object>>) testData.get("test_steps");
        for (Map<String, Object> step : testStepsArray) {
            TestStep testStep = new TestStep();
            testStep.setAction((String) step.get("action"));
            testStep.setExpected((String) step.get("expected"));
            testCase.addSteps(testStep);
        }
        return testCase;
    }


}
