package com.exercise.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ServiceExceptionHandler {

    private ResponseEntity<Object> generateErrorResponse( ApiErrorResponse errorResponse){
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(BusinessValidationException.class)
    @ResponseBody
    public ResponseEntity<Object> handleBusinessValidationException(Exception exception, WebRequest request) {
        BusinessValidationException bexp = (BusinessValidationException) exception;
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorMessage(bexp.getMessage());
        errorResponse.setErrorCode(bexp.getExceptionCode());
        return generateErrorResponse(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException( EntityNotFoundException exception) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND);
        errorResponse.setErrorMessage(exception.getMessage());
        return generateErrorResponse(errorResponse);
    }

    @ExceptionHandler(ForexServiceNotAvailableException.class)
    public ResponseEntity<Object> handleForexServiceNotAvailableException( ForexServiceNotAvailableException exception ){
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponse.setErrorMessage(" Having trouble in getting prices for the package. Please try after some time ");
        return generateErrorResponse(errorResponse);
    }

}
