package com.brichev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ExchangeException.class})
    protected ResponseEntity<?> handleConflict(ExchangeException exception) {
        return new ResponseEntity<>(Collections.singletonList(ErrorCodes.getError(exception.getMessage())), HttpStatus.BAD_REQUEST);
    }
}
