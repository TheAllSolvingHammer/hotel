package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateOutput;
import io.vavr.control.Either;

public class AdminPartialUpdateOperationImpl implements AdminPartialUpdateOperation {

    @Override
    public Either<ErrorsProcessor, AdminPartialUpdateOutput> process(AdminPartialUpdateInput input) {
        return null;
    }
}
