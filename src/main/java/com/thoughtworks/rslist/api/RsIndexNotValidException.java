package com.thoughtworks.rslist.api;

public class RsIndexNotValidException extends RuntimeException {
    private String message;
    public RsIndexNotValidException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
