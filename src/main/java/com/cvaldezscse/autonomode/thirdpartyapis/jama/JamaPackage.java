package com.cvaldezscse.autonomode.thirdpartyapis.jama;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.xml.SuiteXmlParser;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JamaPackage {
    public static void main(String[] args) throws Exception {
        String testngXMLDir = args[0];
        File dir = new File(testngXMLDir);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".xml"))
                processsTestNGXml(file);
        }
    }

    /**
     * Tries to parse the XML files as TestNG test suites iterate through each test.
     */
    private static void processsTestNGXml(File xmlFile) throws Exception {
        SuiteXmlParser parser = new SuiteXmlParser();
        XmlSuite suite = parser.parse(xmlFile.getAbsolutePath(), new FileInputStream(xmlFile), false);
        for (XmlTest test : suite.getTests()) {
            for (String className : (List<String>) test.getXmlClasses().stream().map(c -> c.getName()).collect(Collectors.toList())) {
                processAnotationStringClass(className);
            }
        }
    }

    /**
     * Gets the class for a className and retrieves the JamaTest annotation.
     */
    private static void processAnotationStringClass(String className) throws Exception {
        URL mainClasses = new File("target/classes/").toURI().toURL();
        URL testClasses = new File("target/test-classes/").toURI().toURL();
        URLClassLoader loader = URLClassLoader.newInstance(new URL[]{
                mainClasses,
                testClasses,
        });

        Class<?> clazz = Class.forName(className, false, loader);
        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            String id = (String) annotation.getClass().getMethod("value").invoke(annotation);
            parseJamaTest(id);
        }
    }

    /**
     * Gets the test case and parses the response to save into the test data
     */
    private static void parseJamaTest(String id) {
        GetGlobalIdResponse fromJama = getTestCase(id, JAMA_PROJECT_ID);
        Map<String, Object> jsonTest = new HashMap<>();
        String url = String.format(RCC_JAMA_GET_TESTCASE_WITH_ID_AND_PROJECT_ID_URL,
                fromJama.getId(),
                Integer.parseInt(JAMA_PROJECT_ID));

        jsonTest.put("name", fromJama.getName());
        jsonTest.put("globalId", fromJama.getGlobalId());
        jsonTest.put("url", url);
        jsonTest.put("description", fromJama.getDescription());

        List<Map<String, String>> steps = new ArrayList<>();

        for (Object testCaseStep : fromJama.getTestCaseSteps()) {
            ObjectMapper om = new ObjectMapper();
            Map<String, String> jsonStep = om.convertValue(testCaseStep, Map.class);
            steps.add(jsonStep);
        }

        jsonTest.put("testCaseSteps", steps);

        Map<String, Object> testData = TestDataHelper.getLocalJamaTestData();
        testData.put(fromJama.getGlobalId(), jsonTest);

        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(String.format(TEST_RESOURCES_DIR_WITH_WILDCARD, LOCAL_JAMA_TEST_DATA_FILE_NAME)), testData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
