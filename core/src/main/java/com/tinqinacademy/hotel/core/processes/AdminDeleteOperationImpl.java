package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteInput;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteOutput;
import io.vavr.control.Either;

public class AdminDeleteOperationImpl implements AdminDeleteOperation {

    @Override
    public Either<ErrorsProcessor, AdminDeleteOutput> process(AdminDeleteInput input) {
        return null;
    }
}
