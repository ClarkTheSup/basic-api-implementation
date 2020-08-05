package com.thoughtworks.rslist.api;

public class RsNotValidException extends RuntimeException {
    private String message;
    public RsNotValidException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
