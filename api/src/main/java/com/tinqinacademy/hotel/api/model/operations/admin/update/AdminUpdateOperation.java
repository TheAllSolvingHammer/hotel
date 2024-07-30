package com.tinqinacademy.hotel.api.model.operations.admin.update;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import io.vavr.control.Either;

public interface AdminUpdateOperation extends OperationProcessor<AdminUpdateInput, AdminUpdateOutput> {
    @Override
    Either<ErrorsProcessor, AdminUpdateInput> process(AdminUpdateOutput input);
}
