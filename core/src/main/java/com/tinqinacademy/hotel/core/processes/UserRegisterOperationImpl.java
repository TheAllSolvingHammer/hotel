package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterOperation;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryExceptionCase;
import com.tinqinacademy.hotel.persistence.entities.GuestEntity;
import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import com.tinqinacademy.hotel.persistence.repositorynew.GuestRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.UserRepository;
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

import static io.vavr.API.$;
import static io.vavr.API.Case;


@Service
@Slf4j
public class UserRegisterOperationImpl extends BaseProcess implements UserRegisterOperation {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserRegisterOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator, RoomRepository roomRepository, ReservationRepository reservationRepository, GuestRepository guestRepository, UserRepository userRepository) {
        super(conversionService, errorMapper, validator);
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Either<ErrorsProcessor, UserRegisterOutput> process(@Valid UserRegisterInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Start register person: {}", input);
                    if (roomRepository.findByRoomNumber(input.getRoomNumber()).isEmpty()) {
                        throw new InputException("Room number is wrong");
                    }
                    UUID roomID = roomRepository.findByRoomNumber(input.getRoomNumber())
                            .get()
                            .getId();
                    Optional<UUID> reservationIDOptional = reservationRepository
                            .findByRoomIDAndStartDateAndEndDate(
                                    roomID.toString()
                                    , input.getStartDate(), input.getEndDate());
                    if (reservationIDOptional.isEmpty()) {
                        throw new InputException("Reservation ID is wrong");
                    }
                    
                    ReservationEntity reservationEntity = reservationRepository.getReferenceById(reservationIDOptional.get());
                    List<GuestEntity> guestEntities = input.getUsers().stream().map(e -> GuestEntity.builder()
                            .authority(e.getAuthority())
                            .birthDate(e.getDateOfBirth())
                            .firstName(e.getFirstName())
                            .lastName(e.getLastName())
                            .validity(e.getValidity())
                            .issueDate(e.getIssueDate())
                            .idCardNumber(e.getIdNumber())
                            .phoneNumber(e.getPhone())
                            .build()).toList();
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
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
    private UUID getUUID(UserRegisterInput input){
        Optional<UUID> reservationIDOptional = reservationRepository
                .findByRoomIDAndStartDateAndEndDate(
                        roomID.toString()
                        , input.getStartDate(), input.getEndDate());
        if (reservationIDOptional.isEmpty()) {
            throw new InputException("Reservation ID is wrong");
        }

    }

}
