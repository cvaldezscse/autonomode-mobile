package com.cvaldezscse.autonomode.reporting;


import com.cvaldezscse.autonomode.annotations.AnnotationHelper;
import com.cvaldezscse.autonomode.annotations.JamaTest;
import com.cvaldezscse.autonomode.annotations.JamaTestStep;
import com.cvaldezscse.autonomode.annotations.JsonTest;
import com.cvaldezscse.autonomode.annotations.JsonTestStep;
import com.cvaldezscse.autonomode.annotations.TestSource;
import com.cvaldezscse.autonomode.annotations.TestSourceStep;
import com.cvaldezscse.autonomode.reporting.listeners.ReportingListener;
import com.cvaldezscse.autonomode.reporting.model.Assertion;
import com.cvaldezscse.autonomode.reporting.model.Screenshot;
import com.cvaldezscse.autonomode.reporting.model.TestCase;
import com.cvaldezscse.autonomode.reporting.model.TestExecution;
import com.cvaldezscse.autonomode.reporting.model.TestStep;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.testng.annotations.Listeners;

import static com.cvaldezscse.autonomode.reporting.listeners.ReportingListener.testPlan;
import static java.lang.String.format;

@Listeners(ReportingListener.class)
public abstract class ReportingTestBase {

    protected void addExecutionStep(String executionStep) {
        getCurrentTestExecution().addExecutionStep(executionStep);
    }

    protected void addAssertion(Assertion assertion) {
        getCurrentTestExecution().addAssertion(assertion);
    }

    protected void addLog(String log) {
        getCurrentTestExecution().addLog(log);
    }

    public void addScreenshot(Screenshot screenshot) {
        getCurrentTestExecution().addScreenshot(screenshot);
    }

    public <T> void assertAndReport(String reason, T actual, Matcher<? super T> matcher) {
        Assertion assertion = new Assertion(reason);
        Description expectedDescription = new StringDescription()
                .appendText("\nExpected: ")
                .appendDescriptionOf(matcher);
        assertion.setExpected(expectedDescription.toString());
        Description actualDescription = new StringDescription()
                .appendText("\n     Found: ")
                .appendValue(actual);
        assertion.setActual(actualDescription.toString());
        String step = format("Verify %s", reason);
        addExecutionStep(step);
        logger.info(step);
        addAssertion(assertion);
        if (!matcher.matches(actual)) {
            matcher.describeMismatch(actual, expectedDescription.appendText("\n     Found: "));
            throw new AssertionError(expectedDescription.toString());
        }
    }

    public <T> void assertAndReport(T actual, Matcher<? super T> matcher) {
        assertAndReport(getCurrentTestExecution().getExpected(), actual, matcher);
    }

    protected TestCase getCurrentTestCase() {
        if (this.getClass().isAnnotationPresent(TestSource.class)) {
            TestSource testSource = AnnotationHelper.getTestSource(this.getClass());
            String id = testSource.id();
            return testPlan.getTestByGlobalId(id);
        }
        JamaTest jamaTest = AnnotationHelper.getJamaTest(this.getClass());
        return testPlan.getTestByGlobalId(jamaTest.value());
    }

    protected TestExecution getCurrentTestExecution() {
        Class<?> c = this.getClass();
        String stepId = null;
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            TestSourceStep testSourceStep = AnnotationHelper.getTestSourceStep(c, stackTraceElement.getMethodName());
            if (testSourceStep != null) {
                stepId = testSourceStep.value();
                break;
            }
            JamaTestStep jamaTestStep = AnnotationHelper.getJamaTestStep(c, stackTraceElement.getMethodName());
            if (jamaTestStep != null) {
                stepId = jamaTestStep.value();
                break;
            }
            JsonTestStep jsonTestStep =
                    AnnotationHelper.getJsonTestStep(c, stackTraceElement.getMethodName());
            if (jsonTestStep != null) {
                stepId = jsonTestStep.value();
                break;
            }
        }
        if (stepId == null)
            stepId = "1";
        TestCase test = getCurrentTestCase();
        if (test == null)
            throw new RuntimeException("No test case found for class: " + c.getSimpleName());
        TestStep step = (TestStep) test.getSteps().get(stepId);
        if (step == null)
            throw new RuntimeException("No TestStep for step " + stepId);
        return step;
    }

    protected TestExecution getTestExecution(Class c, String methodName) {
        TestSourceStep testSourceStep = AnnotationHelper.getTestSourceStep(c, methodName);
        JamaTestStep jamaTestStep = AnnotationHelper.getJamaTestStep(c, methodName);
        JsonTestStep jsonTestStep = AnnotationHelper.getJsonTestStep(c, methodName);
        String stepId = null;
        if (testSourceStep != null)
            stepId = testSourceStep.value();
        else if (jamaTestStep != null)
            stepId = jamaTestStep.value();
        else if (jsonTestStep != null)
            stepId = jsonTestStep.value();
        else
            stepId = "1";
        TestCase test = null;
        try {
            test = getCurrentTestCase();
        } catch (Exception e) {
            if (c.isAnnotationPresent(TestSource.class)) {
                TestSource testSource = (TestSource) c.getAnnotation(TestSource.class);
                test = testPlan.getTestByGlobalId(testSource.id());
            } else if (c.isAnnotationPresent(JamaTest.class)) {
                JamaTest jamaTest = (JamaTest) c.getAnnotation(JamaTest.class);
                test = testPlan.getTestByGlobalId(jamaTest.value());
            } else if (c.isAnnotationPresent(JsonTest.class)) {
                JsonTest jsonTest = (JsonTest) c.getAnnotation(JsonTest.class);
                test = testPlan.getTestByGlobalId(jsonTest.value());
            }
        }
        if (test == null)
            throw new RuntimeException("Cannot find test case for class: " + c.getName());
        TestExecution testExecution = test.getSteps().get(stepId);
        if (testExecution == null) {
            testExecution = new TestExecution();
            testExecution.setAction("Auto-generated execution for " + methodName);
            testExecution.setExpected("Auto-generated expected result");
            test.addExecution(stepId, testExecution);
        }
        return testExecution;
    }


}
