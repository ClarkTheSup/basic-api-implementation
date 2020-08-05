package com.thoughtworks.rslist.api;

public class StartEndParamException extends RuntimeException{
    private String message;
    public StartEndParamException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
