package com.tinqinacademy.hotel.api.base;

import io.vavr.control.Either;

public interface OperationProcessor<T extends OperationInput,E extends OperationOutput> {
    Either<ErrorsProcessor,T> process(E input);
}
