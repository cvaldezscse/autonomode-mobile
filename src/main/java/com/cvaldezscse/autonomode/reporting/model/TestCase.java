package com.cvaldezscse.autonomode.reporting.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class TestCase {
    private String testCaseId;
    private String name;
    private String description;
    private String url;
    private String color;
    private Date date;
    private Map<String, TestExecution> steps;

    public TestCase(){
        steps = new HashMap<>();
        date = new Date();
    }

    public void addStep(TestStep step){
        int index = steps.keySet().size() + 1;
        steps.put(Integer.toString(index), step);
    }

    public void addStep(String position, TestStep step) {
        steps.put(position, step);
    }

    public void addExecution(String position, TestExecution execution){
        steps.put(position, execution);
    }

    public TestExecution getStepExecution(String stepId) {
        return steps.get(stepId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TestCase testCase = (TestCase) obj;
        return name.equals(testCase.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
