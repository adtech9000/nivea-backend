package com.nivea_be.nivea_ad.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ImpressionDocumentNotFoundException.class)
    public ResponseEntity<String> handleImpressionDocumentNotFound(ImpressionDocumentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EngagementDocumentNotFoundException.class)
    public ResponseEntity<String> handleEngagementDocumentNotFound(EngagementDocumentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

