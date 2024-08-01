package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomInput;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomOperation;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomOutput;
import io.vavr.control.Either;

public class UserDisplayRoomOperationImpl implements UserDisplayRoomOperation {

    @Override
    public Either<ErrorsProcessor, UserDisplayRoomOutput> process(UserDisplayRoomInput input) {
        return null;
    }
}
