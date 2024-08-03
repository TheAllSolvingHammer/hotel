package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryExceptionCase;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@Slf4j
public class AdminRegisterOperationImpl extends BaseProcess implements AdminRegisterOperation {


    public AdminRegisterOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator) {
        super(conversionService, errorMapper, validator);
    }

    @Override
    public Either<ErrorsProcessor, AdminRegisterOutput> process(@Valid AdminRegisterInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Start admin info: {}", input);
                    //todo
                    AdminRegisterOutput adminRegisterOutput = AdminRegisterOutput.builder()
                            .data(new ArrayList<>(Arrays.asList("1","2","3")))
                            .startDate(input.getStartDate())
                            .endDate(input.getEndDate())
                            .firstName(input.getFirstName())
                            .lastName(input.getLastName())
                            .phone(input.getPhone())
                            .idNumber(input.getIdNumber())
                            .validity(input.getValidity())
                            .authority(input.getAuthority())
                            .issueDate(input.getIssueDate())
                            .build();
                    log.info("End admin info: {}", adminRegisterOutput);
                    return adminRegisterOutput;



        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
}
