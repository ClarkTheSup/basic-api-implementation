package com.thoughtworks.rslist.exception;

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
