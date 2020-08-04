package com.thoughtworks.rslist.model;

import java.io.Serializable;

public class Rs implements Serializable {
    private String name;

    public Rs() {
    }

    public Rs(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
