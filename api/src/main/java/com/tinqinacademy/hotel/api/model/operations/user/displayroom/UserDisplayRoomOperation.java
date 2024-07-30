package com.tinqinacademy.hotel.api.model.operations.user.displayroom;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import io.vavr.control.Either;

public interface UserDisplayRoomOperation extends OperationProcessor<UserDisplayRoomInput, UserDisplayRoomOutput> {
    @Override
    Either<ErrorsProcessor, UserDisplayRoomInput> process(UserDisplayRoomOutput input);
}
