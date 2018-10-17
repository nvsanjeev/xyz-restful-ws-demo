package com.xyz.cta.rest.ws.controller;

import com.xyz.cta.rest.ws.exception.ContactNotFoundException;
import com.xyz.cta.rest.ws.exception.XyzExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class XyzCustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        XyzExceptionResponse xyzExceptionResponse =
                new XyzExceptionResponse(LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity<>(xyzExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ContactNotFoundException.class)
    public final ResponseEntity<Object> handleContactNotFoundException(ContactNotFoundException ex, WebRequest request) {
        XyzExceptionResponse xyzExceptionResponse =
                new XyzExceptionResponse(LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(xyzExceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


        String fieldError = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + "[" + error.getRejectedValue() + "] : " + error.getDefaultMessage())
                .collect(Collectors.joining("\n", "Validation failed for input:\n", ""));

        String globalErrors = ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .collect(Collectors.joining("\n", "\n Gloabal errors:\n", "\n"));

        XyzExceptionResponse xyzExceptionResponse =
                new XyzExceptionResponse(LocalDateTime.now(),
                        "Validation failed",
                        (fieldError + globalErrors));
        return new ResponseEntity<>(xyzExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
