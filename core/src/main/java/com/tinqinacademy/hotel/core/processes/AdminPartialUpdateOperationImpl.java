package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateOutput;
import com.tinqinacademy.hotel.core.exceptionfamilies.InputQueryExceptionCase;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminPartialUpdateOperationImpl extends BaseOperation<AdminPartialUpdateOutput,AdminPartialUpdateInput> implements AdminPartialUpdateOperation {

    @Override
    public Either<ErrorsProcessor, AdminPartialUpdateOutput> process(AdminPartialUpdateInput input) {
        return Try.of(()->{
            log.info("Start admin partial update room: {}", input);
            if(input.getRoomID().equalsIgnoreCase("5A"))
                throw new InputException("Invalid room for partial update");
            AdminPartialUpdateOutput adminPartialUpdateOutput = AdminPartialUpdateOutput.builder()
                    .ID("23234")
                    .build();
            log.info("End admin partial update room: {}", adminPartialUpdateOutput);
            return adminPartialUpdateOutput;

        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable);
    }
}
