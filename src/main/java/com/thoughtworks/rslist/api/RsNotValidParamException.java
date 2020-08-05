package com.thoughtworks.rslist.api;

public class RsNotValidParamException extends RuntimeException{
    private String message;
    public RsNotValidParamException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
