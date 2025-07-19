package com.cvaldezscse.autonomode.reporting.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Assertion {
    private String reason;
    private String expected;
    private String actual;

    public Assertion(String reason){
        this.reason = reason;
    }

}
