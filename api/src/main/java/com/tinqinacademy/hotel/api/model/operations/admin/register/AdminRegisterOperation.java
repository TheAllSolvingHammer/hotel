package com.tinqinacademy.hotel.api.model.operations.admin.register;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import io.vavr.control.Either;

public interface AdminRegisterOperation extends OperationProcessor<AdminRegisterInput,AdminRegisterOutput> {
    @Override
    Either<ErrorsProcessor, AdminRegisterInput> process(AdminRegisterOutput input);
}
