package com.exercise.rest.exception;

public class ForexServiceNotAvailableException  extends RuntimeException{

    private String currency;

    public ForexServiceNotAvailableException(String message) {
        super(message);
    }
    public ForexServiceNotAvailableException(String message, String currency) {
        this(message);
        this.currency = currency;
    }
}
