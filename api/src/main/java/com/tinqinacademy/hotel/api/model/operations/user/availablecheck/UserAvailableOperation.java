package com.tinqinacademy.hotel.api.model.operations.user.availablecheck;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import io.vavr.control.Either;

public interface UserAvailableOperation extends OperationProcessor<UserAvailableInput, UserAvailableOutput> {
    @Override
    Either<ErrorsProcessor, UserAvailableInput> process(UserAvailableOutput input);
}
