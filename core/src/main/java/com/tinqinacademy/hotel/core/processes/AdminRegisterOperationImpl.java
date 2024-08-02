package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOutput;
import com.tinqinacademy.hotel.core.exceptionfamilies.InputQueryExceptionCase;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminRegisterOperationImpl extends BaseOperation<AdminRegisterOutput,AdminRegisterInput> implements AdminRegisterOperation {


    @Override
    public Either<ErrorsProcessor, AdminRegisterOutput> process(AdminRegisterInput input) {
        return Try.of(()->{
                    log.info("Start admin info: {}", input);
                    //must be fixed!!!!!!
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
                .mapLeft(InputQueryExceptionCase::handleThrowable);
    }
}
