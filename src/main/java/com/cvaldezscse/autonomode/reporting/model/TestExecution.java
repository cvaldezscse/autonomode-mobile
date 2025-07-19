package com.cvaldezscse.autonomode.reporting.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class TestExecution {

    private String action = "";
    private String expected = "";;
    private String result = "";
    private String color = "";
    private Map<String, String> executionSteps;
    private Map<String, Assertion> assertions;
    private Map<String, String> logs;
    private Map<String, Screenshot> screenshots;
    private Map<String, Object> data;

    public TestExecution() {
        executionSteps = new HashMap<>();
        assertions = new HashMap<>();
        logs = new HashMap<>();
        screenshots = new HashMap<>();
    }

    public void addExecutionStep(String executionStep) {
        int index = executionSteps.keySet().size() + 1;
        executionSteps.put(Integer.toString(index), executionStep);
    }

    public void addAssertion(Assertion assertion) {
        int index = assertions.keySet().size() + 1;
        assertions.put(Integer.toString(index), assertion);
    }

    public void addLog(String log) {
        int index = logs.keySet().size() + 1;
        logs.put(Integer.toString(index), log);
    }

    public void addScreenshot(Screenshot screenshot) {
        int index = screenshots.keySet().size() + 1;
        screenshots.put(Integer.toString(index), screenshot);
    }


}
