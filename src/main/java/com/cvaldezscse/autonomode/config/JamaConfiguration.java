package com.cvaldezscse.autonomode.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JamaConfiguration {
    boolean jamaEnabled;
    String testGroup;
    String testCycle;
    String testRun;
    String jamaProject;
    boolean runRegression;
    boolean updateTestRunStatus;
    boolean filterByTags;
    int assignedTo;
    String tags;
}
