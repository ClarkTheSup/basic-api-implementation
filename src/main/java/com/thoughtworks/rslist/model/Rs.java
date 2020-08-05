package com.thoughtworks.rslist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Rs implements Serializable {
    @NotNull
    private String name;
    @NotNull
    private String keyword;
    @NotNull
    private User user;

    public Rs() {
    }

    public Rs(String name, String keyword, User user) {
        this.name = name;
        this.keyword = keyword;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    //@JsonIgnore
    public User getUser() {
        return user;
    }

    @JsonProperty
    public void setUser(User user) {
        this.user = user;
    }
}
