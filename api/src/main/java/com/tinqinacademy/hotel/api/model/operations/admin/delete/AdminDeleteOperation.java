package com.tinqinacademy.hotel.api.model.operations.admin.delete;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import io.vavr.control.Either;

public interface AdminDeleteOperation extends OperationProcessor<AdminDeleteInput, AdminDeleteOutput> {
    @Override
    Either<ErrorsProcessor, AdminDeleteInput> process(AdminDeleteOutput input);
}
