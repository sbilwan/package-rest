package com.exercise.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * Error Response from the API.
 *
 */
public class ApiErrorResponse {

    private HttpStatus status;

    private String errorMessage;

    private long errorCode;


    ApiErrorResponse() {

    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }

    ApiErrorResponse(HttpStatus status) {
        this();

        this.status = status;
    }

    ApiErrorResponse(HttpStatus status, String message) {
        this();
        this.errorMessage = message;
    }

    ApiErrorResponse(HttpStatus status, String message, long code) {
        this();
        this.errorMessage = message;
        this.errorCode = code;
    }

}
