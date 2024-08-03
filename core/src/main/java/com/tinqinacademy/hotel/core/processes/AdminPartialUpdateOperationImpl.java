package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryExceptionCase;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminPartialUpdateOperationImpl extends BaseProcess implements AdminPartialUpdateOperation {

    public AdminPartialUpdateOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator) {
        super(conversionService, errorMapper, validator);
    }

    @Override
    public Either<ErrorsProcessor, AdminPartialUpdateOutput> process(@Valid AdminPartialUpdateInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
            //todo
            log.info("Start admin partial update room: {}", input);
            if(input.getRoomID().equalsIgnoreCase("5A"))
                throw new InputException("Invalid room for partial update");
            AdminPartialUpdateOutput adminPartialUpdateOutput = AdminPartialUpdateOutput.builder()
                    .ID("23234")
                    .build();
            log.info("End admin partial update room: {}", adminPartialUpdateOutput);
            return adminPartialUpdateOutput;

        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable))
                ;
    }
}
