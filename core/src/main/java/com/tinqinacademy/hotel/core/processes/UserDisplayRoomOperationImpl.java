package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ConverterException;
import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import com.tinqinacademy.hotel.api.exceptions.QueryException;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomInput;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomOperation;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.QueryConverterExceptionCase;
import com.tinqinacademy.hotel.core.families.converters.ReservationEntityToLocalEndDate;
import com.tinqinacademy.hotel.core.families.converters.ReservationEntityToLocalStartDate;
import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.repositorynew.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Service
@Slf4j
public class UserDisplayRoomOperationImpl extends BaseProcess implements UserDisplayRoomOperation{
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationEntityToLocalStartDate reservationToStartDate;
    private final ReservationEntityToLocalEndDate reservationToEndDate;

    @Autowired
    public UserDisplayRoomOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator, RoomRepository roomRepository, ReservationRepository reservationRepository, ReservationEntityToLocalStartDate reservationToStartDate, ReservationEntityToLocalEndDate reservationToEndDate) {
        super(conversionService, errorMapper, validator);
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.reservationToStartDate = reservationToStartDate;
        this.reservationToEndDate = reservationToEndDate;
    }

    @Override
    public Either<ErrorsProcessor, UserDisplayRoomOutput> process(@Valid UserDisplayRoomInput input) {
        log.info("Start display room: {}", input);
        return validateInput(input).flatMap(validInput -> Try.of(()-> {
            RoomEntity roomEntity = roomRepository.getReferenceById(input.getRoomID());
            List<ReservationEntity> reservationEntityList=getReservationEntityList(input.getRoomID());
            List<LocalDate> startDates=reservationToStartDate.convert(reservationEntityList);
            List<LocalDate> endDates=reservationToEndDate.convert(reservationEntityList);
                    checkConversion(startDates, endDates);
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
                .mapLeft(QueryConverterExceptionCase::handleThrowable));
    }

    private void checkConversion(List<LocalDate> startDates, List<LocalDate> endDates) {
        if(startDates ==null || endDates ==null){
            throw new ConverterException("Wrong conversion of dates");
        }
    }

    private List<ReservationEntity> getReservationEntityList(UUID roomID) {
        return Optional.ofNullable(reservationRepository.findByRoomId(roomID))
                .filter(List::isEmpty)
                .orElseThrow(()->new QueryException("No reservations found for room ID: " + roomID));
    }
}
