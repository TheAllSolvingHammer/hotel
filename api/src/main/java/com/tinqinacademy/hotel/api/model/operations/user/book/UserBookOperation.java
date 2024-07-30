package com.tinqinacademy.hotel.api.model.operations.user.book;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import io.vavr.control.Either;

public interface UserBookOperation extends OperationProcessor<UserBookInput,UserBookOutput> {
    @Override
    Either<ErrorsProcessor, UserBookInput> process(UserBookOutput input);
}
