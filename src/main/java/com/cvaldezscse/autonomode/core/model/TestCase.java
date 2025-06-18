package com.cvaldezscse.autonomode.core.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class TestCase {

    private String globalId;
    private String name;
    private String description;
    private String url;
    private String color;
    private Date date;
    private Map<String, TestExecution> steps;

    /**
     * Initializes a new TestCase with an empty steps collection.
     */
    public TestCase() {
        steps = new HashMap<>();
        date = new Date();
    }

    /**
     * Adds a TestStep to the test case.
     *
     * @param step The TestStep to add
     */
    public void addSteps(TestStep step) {
        int index = steps.keySet().size() + 1;
        steps.put(Integer.toString(index), step);
    }

    /**
     * Adds a TestStep at a specific position in the test case.
     *
     * @param position The position/ID where to add the step
     * @param step     The TestStep to add
     */
    public void addSteps(String position, TestStep step) {
        steps.put(position, step);
    }

    /**
     * Adds a TestExecution to the test case.
     *
     * @param position  The position/ID where to add the execution
     * @param execution The TestExecution to add
     */
    public void addExecution(String position, TestExecution execution) {
        steps.put(position, execution);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestCase testCase = (TestCase) o;
        return name.equals(testCase.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    /**
     * Gets a step execution by its ID.
     *
     * @param stepId The ID of the step to retrieve
     * @return The TestExecution for the specified step ID
     */
    public TestExecution getStepExecution(String stepId) {
        return steps.get(stepId);
    }
}
