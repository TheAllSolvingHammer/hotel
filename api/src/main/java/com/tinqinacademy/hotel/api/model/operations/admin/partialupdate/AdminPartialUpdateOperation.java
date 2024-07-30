package com.tinqinacademy.hotel.api.model.operations.admin.partialupdate;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import io.vavr.control.Either;

public interface AdminPartialUpdateOperation extends OperationProcessor<AdminPartialUpdateInput, AdminPartialUpdateOutput> {
    @Override
    Either<ErrorsProcessor, AdminPartialUpdateInput> process(AdminPartialUpdateOutput input);
}
