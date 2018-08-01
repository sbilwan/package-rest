package com.exercise.rest.exception;

public class BusinessValidationException extends  RuntimeException{

    private String exceptionMessage;

    private long exceptionCode;

    private Object errorObject;

    public BusinessValidationException() {
        super();
    }

    public BusinessValidationException(String message) {
        this();
        this.exceptionMessage = message;
    }

    public BusinessValidationException(String message, long code) {
        this();
        this.exceptionMessage = message;
        this.exceptionCode = code;
    }

    public BusinessValidationException(String message, Object cause) {
        this();
        this.exceptionMessage = message;
        this.errorObject = cause;
    }

    @Override
    public String getMessage() {
        return exceptionMessage;

    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public long getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(long exceptionCode) {
        this.exceptionCode = exceptionCode;
    }


    public Object getErrorObject() {
        return errorObject;
    }
}
