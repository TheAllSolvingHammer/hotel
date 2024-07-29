package com.tinqinacademy.hotel.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Component
public class ExceptionServiceImpl implements ExceptionService {
    @Override
    public ResponseErrorBody handle(MethodArgumentNotValidException ex) {
        List<ErrorWrapper> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(ErrorWrapper.builder()
                        .message(error.getDefaultMessage())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDate.now())
                        .build()));
        return ResponseErrorBody.builder()
                .errors(errors)
                .build();
    }

    @Override
    public ResponseErrorBody handle(InputException ex) {
        ErrorWrapper errorWrapper = ErrorWrapper.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDate.now())
                .build();
        List<ErrorWrapper> errorWrappers = new ArrayList<>();
        errorWrappers.add(errorWrapper);
        return ResponseErrorBody.builder()
                .errors(errorWrappers)
                .build();
    }

//    @Override
//    public ResponseErrorBody handle(Exception ex) {
//        ErrorWrapper errorWrapper= ErrorWrapper.builder()
//                .message(ex.)
//                .status(HttpStatus.BAD_REQUEST)
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .timestamp(LocalDate.now())
//                .build();
//        List<ErrorWrapper> errorWrappers = new ArrayList<>();
//        errorWrappers.add(errorWrapper);
//        return ResponseErrorBody.builder()
//                .errors(errorWrappers)
//                .build();
//    }


}
