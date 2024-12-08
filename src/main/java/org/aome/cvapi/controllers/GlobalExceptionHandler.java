package org.aome.cvapi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.aome.cvapi.util.exceptions.*;
import org.aome.cvapi.util.responses.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ProfileNotSaveException.class, ProfileNotGetException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ExceptionResponse internalServerErrorsHandler(RuntimeException error){
        log.error(error.getMessage());
        return new ExceptionResponse(error.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ExceptionResponse validationExceptionHandler(RuntimeException validationException){
        log.error(validationException.getMessage());
        return new ExceptionResponse(validationException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({ProfileNotFoundException.class, ImageNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ExceptionResponse notFoundExceptionHandler(RuntimeException profileNotFoundException){
        log.error(profileNotFoundException.getMessage());
        return new ExceptionResponse(profileNotFoundException.getMessage(), LocalDateTime.now());
    }
}

