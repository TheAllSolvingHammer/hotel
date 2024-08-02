package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteInput;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteOutput;
import com.tinqinacademy.hotel.core.exceptionfamilies.InputQueryExceptionCase;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminDeleteOperationImpl extends BaseOperation<AdminDeleteOutput,AdminDeleteInput> implements AdminDeleteOperation {
    private final RoomRepository roomRepository;
    @Override
    public Either<ErrorsProcessor, AdminDeleteOutput> process(AdminDeleteInput input) {
       return Try.of(()->{
            log.info("Start admin delete room: {}", input);
            Optional<RoomEntity> roomEntityOptional= roomRepository.findById(input.getID());
            if(roomEntityOptional.isEmpty()){
                throw new InputException("Room not found");
            }
            roomRepository.deleteById(input.getID());
            AdminDeleteOutput adminDeleteOutput = AdminDeleteOutput.builder()
                    .message("Successfully deleted room with ID: "+input.getID())
                    .build();
            log.info("End admin delete room: {}", adminDeleteOutput);
            return adminDeleteOutput;
        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable);
    }
}
