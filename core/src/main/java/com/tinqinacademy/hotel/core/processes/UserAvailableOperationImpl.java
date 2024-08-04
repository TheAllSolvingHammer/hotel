package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.exceptions.QueryException;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableInput;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableOperation;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryExceptionCase;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class UserAvailableOperationImpl extends BaseProcess implements UserAvailableOperation {

    private final RoomRepository roomRepository;
    @Autowired
    public UserAvailableOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator, RoomRepository roomRepository) {
        super(conversionService, errorMapper, validator);
        this.roomRepository = roomRepository;
    }


    @Override
    public Either<ErrorsProcessor, UserAvailableOutput> process(@Valid UserAvailableInput input) {
        log.info("Start check availability: {}", input);
        return validateInput(input).flatMap(validInput -> Try.of(()-> {
            UserAvailableOutput userAvailableOutput = UserAvailableOutput.builder()
                    .id(getListCheck(input))
                    .build();
            log.info("End check availability: {}", userAvailableOutput);
            return userAvailableOutput;
        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
    private Bed getBedCheck(String bed) {
        return Optional.ofNullable(Bed.getByCode(bed))
                .filter(b -> b != Bed.UNKNOWN)
                .orElseThrow(() -> new InputException("Invalid bed code"));
    }

    private BathRoom getBathroomCheck(String bathroom){
        return Optional.ofNullable(BathRoom.getByCode(bathroom))
                .filter(b->b!=BathRoom.UNKNOWN)
                .orElseThrow(() -> new InputException("Invalid bathroom code"));
    }

    private List<UUID> getListCheck(UserAvailableInput input){
        return Optional.ofNullable(roomRepository.findByCustom(input.getEndDate(),
                        input.getStartDate(),
                        getBathroomCheck(input.getBathRoom()).name(),
                        getBedCheck(input.getBed()).name()))
                .filter(rooms -> !rooms.isEmpty())
                .orElseThrow(() -> new QueryException("No rooms available for the given criteria"));
    }

}
