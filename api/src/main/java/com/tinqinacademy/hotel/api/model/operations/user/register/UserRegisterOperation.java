package com.tinqinacademy.hotel.api.model.operations.user.register;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import io.vavr.control.Either;

public interface UserRegisterOperation extends OperationProcessor<UserRegisterInput,UserRegisterOutput> {
    @Override
    Either<ErrorsProcessor, UserRegisterInput> process(UserRegisterOutput input);
}
