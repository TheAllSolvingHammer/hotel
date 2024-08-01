package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.base.OperationOutput;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;
@Slf4j
public abstract class BaseOperation<T extends OperationOutput,E extends OperationInput> implements OperationProcessor<T,E> {
    @Override
    public Either<ErrorsProcessor, T> process(E input) {
        return Try.of(() -> execute(input))
                .toEither()
                .mapLeft(throwable -> API.Match(throwable).of(
                        Case($(instanceOf(InputException.class)), e -> {
                            log.error("Invalid input: {}", e.getMessage());
                            return ErrorsProcessor.builder()
                                    .httpStatus(HttpStatus.NOT_FOUND)
                                    .statusCode(HttpStatus.NOT_FOUND.value())
                                    .message(e.getMessage())
                                    .build();
                        })
                        // other exceptions
                        // Case($(instanceOf()))
                ));
    }

    protected abstract T execute(E input) throws Exception;
}
