package com.cvaldezscse.autonomode.support.testdata;

import com.cvaldezscse.autonomode.reporting.model.TestCase;
import com.cvaldezscse.autonomode.reporting.model.TestStep;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TestDataHelper {
    private static ObjectMapper mapper = null;
    private static Map<String, Object> testdata = null;

    /**
     * Method for getting the test data filtering by Global ID
     *
     * @param globalID String Jama ID which is at the beginning of each test class with the format @TestSource(id = "GID-XYZABC")
     * @return Map Object with the respective value in the test-data.json file
     */
    public static Map<String, Object> getTestData(String globalID) {
        Map<String, Object> data;
        data = (Map<String, Object>) getTestData().get(globalID);
        if (data == null || data.isEmpty()) {
            throw new RuntimeException("No Json Data found for " + globalID);
        }
        return data;
    }

    /**
     * Method for getting the test data filtering by JamaProject ID
     *
     * @param jamaProjectId String Jama ID which is at the beginning of each test class with the format @TestSource(id = "GID-XYZABC")
     * @return Map Object with the respective value in the test-data.json file
     */
    public static Map<String, Object> getJamaTestData(String jamaProjectId) {
        Map<String, Object> data = (Map<String, Object>) getLocalJamaTestData().get(jamaProjectId);
        if (data == null || data.isEmpty()) {
            throw new RuntimeException("No Json Data found for " + jamaProjectId);
        }
        return data;
    }

    /**
     * Method for getting the TestCase from the test-data.json filtering by JamaProject ID
     *
     * @param jamaProjectId String Jama ID which is at the beginning of each test class with the format @TestSource(id = "GID-XYZABC")
     * @return TestCase for the id
     */
    public static TestCase asTestCase(String jamaProjectId) throws JsonProcessingException {
        Map<String, Object> data = getJamaTestData(jamaProjectId);
        TestCase testCase = new TestCase();
        testCase.setName((String) data.get("name"));
        testCase.setGlobalId((String) data.get("globalId"));
        testCase.setDescription((String) data.get("description"));
        for (Map testCaseStep : (List<Map>) data.get("testCaseSteps")) {
            TestStep step = new TestStep();
            step.setAction((String) testCaseStep.getOrDefault("action", ""));
            step.setExpected((String) testCaseStep.getOrDefault("expectedResult", ""));
            testCase.addSteps(step);
        }
        testCase.setUrl((String) data.getOrDefault("url", ""));
        return testCase;
    }

    /**
     * An abstraction of the getTestData(String) method
     *
     * @return Map Object with the respective value in the test-data.json file
     */
    public static Map<String, Object> getTestData() {
        if (testdata == null) {
            try {
                testdata = initMapper().readValue(
                        new File(String.format(TEST_RESOURCES_DIR_WITH_WILDCARD, String.format(DEFAULT_TEST_DATA_FILE_NAME, ENVIRONMENT.toLowerCase()))),
                        new TypeReference<Map<String, Object>>() {
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return testdata;
    }

    /**
     * An abstraction of the getTestData(String) method
     *
     * @return Map Object with the respective value in the test-data.json file
     */
    public static Map<String, Object> getLocalJamaTestData() {
        if (testdata == null) {
            try {
                testdata = initMapper().readValue(
                        new File(String.format(TEST_RESOURCES_DIR_WITH_WILDCARD, LOCAL_JAMA_TEST_DATA_FILE_NAME)),
                        new TypeReference<Map<String, Object>>() {
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return testdata;
    }

    /**
     * Utility for mapping the JSON file elements
     *
     * @return ObjectMapper object used to deserialize the object later
     */
    public static ObjectMapper initMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper()
                    // json is only a partial object, containing name, desc, steps
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        }
        return mapper;
    }
}
