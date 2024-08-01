package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateOutput;
import io.vavr.control.Either;

public class AdminUpdateOperationImpl implements AdminUpdateOperation {


    @Override
    public Either<ErrorsProcessor, AdminUpdateOutput> process(AdminUpdateInput input) {
        return null;
    }
}
