package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteInput;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryExceptionCase;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AdminDeleteOperationImpl extends BaseProcess implements AdminDeleteOperation {
    private final RoomRepository roomRepository;
    @Autowired
    public AdminDeleteOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator, RoomRepository roomRepository) {
        super(conversionService, errorMapper, validator);
        this.roomRepository = roomRepository;
    }

    @Override
    public Either<ErrorsProcessor, AdminDeleteOutput> process(AdminDeleteInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
            log.info("Start admin delete room: {}", input);
                    verifyRoom(input);
                    roomRepository.deleteById(input.getID());
            AdminDeleteOutput adminDeleteOutput = AdminDeleteOutput.builder()
                    .message("Successfully deleted room with ID: "+input.getID())
                    .build();
            log.info("End admin delete room: {}", adminDeleteOutput);
            return adminDeleteOutput;
        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }

    private void verifyRoom(AdminDeleteInput input) {
        Optional<RoomEntity> roomEntityOptional= roomRepository.findById(input.getID());
        if(roomEntityOptional.isEmpty()){
            throw new InputException("Room not found");
        }
    }
}
