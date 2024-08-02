package com.tinqinacademy.hotel.core.exceptionfamilies;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.ErrorWrapper;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.exceptions.QueryException;
import io.vavr.API;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;
@Slf4j
public class InputQueryExceptionCase {
    public static ErrorsProcessor handleThrowable(Throwable throwable) {
        return API.Match(throwable).of(
                Case($(instanceOf(InputException.class)), e -> {
                    log.error("Invalid input: {}", e.getMessage());
                    return ErrorsProcessor.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build();
                }),
                Case($(instanceOf(QueryException.class)), e -> {
                    log.error("Invalid query: {}", e.getMessage());
                    return ErrorsProcessor.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build();
                }));
    }
}
