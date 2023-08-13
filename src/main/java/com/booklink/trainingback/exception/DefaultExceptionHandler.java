package com.booklink.trainingback.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(NotFoundException exception){
        this.logger.info(exception.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(SpecialCharacterException.class)
    protected ResponseEntity<Object> handleSpecialCharacterNotFound(SpecialCharacterException exception){
        this.logger.info(exception.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
