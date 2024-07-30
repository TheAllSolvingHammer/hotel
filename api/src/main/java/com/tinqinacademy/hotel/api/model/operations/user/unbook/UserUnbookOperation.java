package com.tinqinacademy.hotel.api.model.operations.user.unbook;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import io.vavr.control.Either;

public interface UserUnbookOperation extends OperationProcessor<UserUnbookInput,UserUnbookOutput> {
    @Override
    Either<ErrorsProcessor, UserUnbookInput> process(UserUnbookOutput input);
}
