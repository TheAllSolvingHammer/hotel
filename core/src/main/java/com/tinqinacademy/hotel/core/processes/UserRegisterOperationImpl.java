package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterOperation;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterOutput;
import io.vavr.control.Either;

public class UserRegisterOperationImpl implements UserRegisterOperation {

    @Override
    public Either<ErrorsProcessor, UserRegisterOutput> process(UserRegisterInput input) {
        return null;
    }
}
