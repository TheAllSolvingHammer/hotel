package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateOutput;
import io.vavr.control.Either;

public class AdminCreateOperationImpl implements AdminCreateOperation {

    @Override
    public Either<ErrorsProcessor, AdminCreateOutput> process(AdminCreateInput input) {
        return null;
    }
}
