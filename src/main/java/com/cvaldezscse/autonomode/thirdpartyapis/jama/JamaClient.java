package com.cvaldezscse.autonomode.thirdpartyapis.jama;

import com.cvaldezscse.autonomode.annotations.JamaTest;
import com.cvaldezscse.autonomode.config.JamaConfiguration;
import com.cvaldezscse.autonomode.reporting.model.TestCase;
import com.cvaldezscse.autonomode.reporting.model.TestStep;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JamaClient {
    protected JamaConfiguration jamaConfig = new JamaConfiguration(jamaConfiguration);
    public static JamaHelper jamaHelper = new JamaHelper();
    private static final Logger logger = LoggerManager.getLogger(JamaClient.class);

    /**
     * Creates a test case structure that is compatible with the test report
     *
     * @param jamaTest Annotation that is at the beginning of each test class that includes a String that indicates the GlobalID
     * @return TestCase Object that has all the test details (including descriptions, links and Steps)
     */
    public static TestCase createTestCaseFromJamaDetails(JamaTest jamaTest) throws IOException {
        if (jamaTest.value() == null)
            throw new RuntimeException(EXCEPTION_NO_JAMA_TEST);
        logger.info(jamaTest.value());
        GetGlobalIdResponse fromJama = getTestCase(jamaTest.value(), JAMA_PROJECT_ID);
        TestCase testCase = new TestCase();
        testCase.setName(fromJama.getName());
        testCase.setGlobalId(fromJama.getGlobalId());
        testCase.setDescription(fromJama.getDescription().trim());
        for (Object testCaseStep : fromJama.getTestCaseSteps()) {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(testCaseStep);
            JsonNode jsonNode = om.readTree(json);
            TestStep step = new TestStep();
            step.setAction(String.valueOf(jsonNode.get("action").asText()));
            step.setExpected(String.valueOf(jsonNode.get("expectedResult").asText()));
            testCase.addSteps(step);
        }
        String url = String.format(RCC_JAMA_GET_TESTCASE_WITH_ID_AND_PROJECT_ID_URL,
                fromJama.getId(),
                Integer.parseInt(JAMA_PROJECT_ID)
        );
        testCase.setUrl(url);
        return testCase;
    }
}
