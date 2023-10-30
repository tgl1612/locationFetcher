package com.lauraproject.locationfetcher.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global handler to nicely wrap exceptions thrown by the application code.
 */
@Slf4j
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException ex) {
        log(ex);
        var err = new AppError();
        err.setMessage(ex.getMessage());
        err.setCode(Integer.toString(HttpStatus.NOT_FOUND.value()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(err);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationException(ValidationException ex){
        log(ex);
        var err = new AppError();
        err.setMessage(ex.getMessage());
        err.setCode(Integer.toString(HttpStatus.BAD_REQUEST.value()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(err);
    }

    private void log(Exception ex) {
        log.error("global handler captured an exception", ex);
    }

}
