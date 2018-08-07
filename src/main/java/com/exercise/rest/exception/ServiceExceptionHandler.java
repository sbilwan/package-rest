package com.exercise.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


/**
 * The Centralized Handler of all the exceptions in the API.
 *
 */
@RestControllerAdvice
public class ServiceExceptionHandler {

    private static  final Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

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
        logger.error(" Business Validation Exception " + bexp.getMessage());
        return generateErrorResponse(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException( EntityNotFoundException exception) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND);
        errorResponse.setErrorMessage(exception.getMessage());
        logger.error("Entity Not Found Exception " + exception.getMessage());
        return generateErrorResponse(errorResponse);
    }

    @ExceptionHandler(ForexServiceNotAvailableException.class)
    public ResponseEntity<Object> handleForexServiceNotAvailableException( ForexServiceNotAvailableException exception ){
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponse.setErrorMessage(" Having trouble in getting prices for the package. Please try after some time ");
        logger.error("ForexService Unavailable  Exception " + exception.getMessage());
        return generateErrorResponse(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleGeneralRuntimeException( RuntimeException exception ){
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponse.setErrorMessage(" Internal Server Error ");
        logger.error("Runtime Exception " + exception.getMessage());
        return generateErrorResponse(errorResponse);
    }

}
