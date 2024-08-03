package com.tinqinacademy.hotel.rest.controller;

import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationOutput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public abstract class BaseController {

    public ResponseEntity<?> handleOperation(Either<ErrorsProcessor, ? extends OperationOutput> result) {
        return result.fold(
                error -> ResponseEntity.status(error.getHttpStatus()).body(error),
                success-> ResponseEntity.status(HttpStatus.OK.value()).body(success)
        );

    }
}
