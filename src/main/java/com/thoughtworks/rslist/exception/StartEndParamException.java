package com.thoughtworks.rslist.exception;

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
