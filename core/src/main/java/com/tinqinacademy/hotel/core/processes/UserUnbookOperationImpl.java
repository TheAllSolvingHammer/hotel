package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookInput;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookOperation;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookOutput;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class UserUnbookOperationImpl implements UserUnbookOperation {

    @Override
    public Either<ErrorsProcessor, UserUnbookOutput> process(UserUnbookInput input) {
        return null;
    }
}
