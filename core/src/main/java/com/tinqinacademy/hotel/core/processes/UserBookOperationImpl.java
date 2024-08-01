package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookInput;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookOperation;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookOutput;
import io.vavr.control.Either;

public class UserBookOperationImpl implements UserBookOperation {


    @Override
    public Either<ErrorsProcessor, UserBookOutput> process(UserBookInput input) {
        return null;
    }
}
