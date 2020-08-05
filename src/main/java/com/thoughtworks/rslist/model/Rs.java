package com.thoughtworks.rslist.model;

import java.io.Serializable;

public class Rs implements Serializable {
    private String name;
    private String keyword;

    public Rs() {
    }

    public Rs(String name, String keyword) {
        this.name = name;
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return name + "-" + keyword;
    }

    public String getName() {
        return name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
