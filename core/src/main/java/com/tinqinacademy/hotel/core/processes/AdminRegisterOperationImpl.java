package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOutput;
import io.vavr.control.Either;

public class AdminRegisterOperationImpl implements AdminRegisterOperation {


    @Override
    public Either<ErrorsProcessor, AdminRegisterOutput> process(AdminRegisterInput input) {
        return null;
    }
}
