package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.exceptions.QueryException;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableInput;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableOperation;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableOutput;
import com.tinqinacademy.hotel.core.exceptionfamilies.InputQueryExceptionCase;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserAvailableOperationImpl extends BaseOperation<UserAvailableOutput,UserAvailableInput> implements UserAvailableOperation {

    private final RoomRepository roomRepository;

    @Override
    public Either<ErrorsProcessor, UserAvailableOutput> process(UserAvailableInput input) {
        log.info("Start check availability: {}", input);
        return Try.of(()-> {
            UserAvailableOutput userAvailableOutput = UserAvailableOutput.builder()
                    .id(getListCheck(input))
                    .build();
            log.info("End check availability: {}", userAvailableOutput);
            return userAvailableOutput;
        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable);
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
