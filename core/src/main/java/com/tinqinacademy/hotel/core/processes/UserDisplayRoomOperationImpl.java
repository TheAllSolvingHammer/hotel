package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.exceptions.QueryException;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableOutput;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomInput;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomOperation;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomOutput;
import com.tinqinacademy.hotel.core.exceptionfamilies.InputQueryEntityExceptionCase;
import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.repositorynew.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDisplayRoomOperationImpl extends BaseOperation<UserDisplayRoomOutput,UserDisplayRoomInput> implements UserDisplayRoomOperation {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public Either<ErrorsProcessor, UserDisplayRoomOutput> process(UserDisplayRoomInput input) {
        log.info("Start display room: {}", input);
        return Try.of(()-> {
            RoomEntity roomEntity = getRoomEntity(input.getRoomID());
            List<ReservationEntity> reservationEntityList=reservationRepository.findByRoomId(roomEntity.getId());
            List<LocalDate> startDates=reservationEntityList.stream().map(ReservationEntity::getStartDate).toList();
            List<LocalDate> endDates=reservationEntityList.stream().map(ReservationEntity::getEndDate).toList();

            UserDisplayRoomOutput userDisplayRoomOutput = UserDisplayRoomOutput.builder()
                    .ID(roomEntity.getId())
                    .price(roomEntity.getPrice())
                    .floor(roomEntity.getFloor())
                    .bedSize(roomEntity.getBedList().stream().map(bed -> Bed.getByCode(bed.getType().toString())).filter(bed1 -> !bed1.equals(Bed.UNKNOWN)).toList())
                    .bathRoom(BathRoom.getByCode(roomEntity.getBathTypes().toString()))
                    .datesOccupied(List.of(startDates,endDates))
                    .build();
            log.info("End display room: {}", userDisplayRoomOutput);
            return userDisplayRoomOutput;
        }).toEither()
                .mapLeft(InputQueryEntityExceptionCase::handleThrowable);
    }
    private RoomEntity getRoomEntity(UUID roomID) {
        return roomRepository.getReferenceById(roomID);
    }
    private List<ReservationEntity> getReservationEntityList(UUID roomID) {
        return Optional.ofNullable(reservationRepository.findByRoomId(roomID))
                .filter(List::isEmpty)
                .orElseThrow(()->new QueryException("No reservations found for room ID: " + roomID));
    }
}
