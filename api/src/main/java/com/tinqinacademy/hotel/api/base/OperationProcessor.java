package com.tinqinacademy.hotel.api.base;

import io.vavr.control.Either;

public interface OperationProcessor<T extends OperationOutput,E extends OperationInput> {
    Either<ErrorsProcessor,T> process(E input);
}
