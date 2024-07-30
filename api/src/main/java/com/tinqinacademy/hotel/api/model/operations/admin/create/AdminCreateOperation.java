package com.tinqinacademy.hotel.api.model.operations.admin.create;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import io.vavr.control.Either;

public interface AdminCreateOperation extends OperationProcessor<AdminCreateInput,AdminCreateOutput> {
    @Override
    Either<ErrorsProcessor, AdminCreateInput> process(AdminCreateOutput input);
}
