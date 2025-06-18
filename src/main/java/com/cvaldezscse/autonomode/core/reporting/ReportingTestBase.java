package com.cvaldezscse.autonomode.core.reporting;

import com.cvaldezscse.autonomode.core.listeners.ReportingClassListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;

@Listeners(ReportingClassListener.class)
public abstract class ReportingTestBase {
    private static final Logger logger = LoggerFactory.getLogger(ReportingTestBase.class);


}
