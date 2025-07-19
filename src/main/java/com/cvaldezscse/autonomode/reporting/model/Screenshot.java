package com.cvaldezscse.autonomode.reporting.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Screenshot {
    private String name;
    private String description;

    public Screenshot(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
