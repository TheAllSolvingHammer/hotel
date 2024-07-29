package com.tinqinacademy.hotel.api.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {
    private final ExceptionService exception;

    @Autowired
    public GlobalExceptionHandler(ExceptionService exception) {
        this.exception = exception;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorBody> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.handle(ex));
    }

    @ExceptionHandler(InputException.class)
    public ResponseEntity<ResponseErrorBody> handleInputException(InputException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.handle(ex));
    }
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ResponseErrorBody> handleException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.handle(ex));
//    }
}
