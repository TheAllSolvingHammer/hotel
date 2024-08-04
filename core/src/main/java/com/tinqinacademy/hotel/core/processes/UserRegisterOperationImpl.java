package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ConverterException;
import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.QueryException;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterOperation;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.QueryConverterExceptionCase;
import com.tinqinacademy.hotel.core.families.converters.RegisterInputToGuestConverter;
import com.tinqinacademy.hotel.persistence.entities.GuestEntity;
import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.repositorynew.GuestRepository;
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

import java.util.List;

import java.util.Optional;
import java.util.UUID;



@Service
@Slf4j
public class UserRegisterOperationImpl extends BaseProcess implements UserRegisterOperation {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final RegisterInputToGuestConverter registerInputToGuest;

    @Autowired
    public UserRegisterOperationImpl(ConversionService conversionService
            , ErrorsProcessor errorMapper
            , Validator validator
            , RoomRepository roomRepository
            , ReservationRepository reservationRepository
            , GuestRepository guestRepository
            , RegisterInputToGuestConverter registerInputToGuest) {
        super(conversionService, errorMapper, validator);
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.registerInputToGuest = registerInputToGuest;
    }

    @Override
    public Either<ErrorsProcessor, UserRegisterOutput> process(@Valid UserRegisterInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Start register person: {}", input);
                    UUID roomID=getRoomUUID(input);
                    UUID reservationID=getReservationUUID(input,roomID);
                    ReservationEntity reservationEntity = reservationRepository.getReferenceById(reservationID);
                    List<GuestEntity> guestEntities = registerInputToGuest.convert(input);
                    checkConversion(guestEntities);
                    guestRepository.saveAll(guestEntities);
                    reservationEntity.setGuests(guestEntities);
                    reservationRepository.flush();
                    UserRegisterOutput userRegisterOutput = UserRegisterOutput.builder()
                            .message("Successfully registered room")
                            .build();
                    log.info("End register person: {}", userRegisterOutput);
                    return userRegisterOutput;
                })
                .toEither()
                .mapLeft(QueryConverterExceptionCase::handleThrowable));
    }

    private void checkConversion(List<GuestEntity> guestEntities) {
        if(guestEntities ==null){
            throw new ConverterException("Error in converting");
        }
    }

    private UUID getReservationUUID(UserRegisterInput input,UUID roomID) {
        Optional<UUID> reservationOptional =reservationRepository
                .findByRoomIDAndStartDateAndEndDate(
                        roomID,
                        input.getStartDate(),
                        input.getEndDate());
        if(reservationOptional.isEmpty()){
            throw new QueryException("Reservation ID is missing");
        }
        return reservationOptional.get();
    }

    private UUID getRoomUUID(UserRegisterInput input){
        return roomRepository.findByRoomNumber(input.getRoomNumber())
                .map(RoomEntity::getId)
                .orElseThrow(() -> new QueryException("Room number is wrong"));
    }

}
