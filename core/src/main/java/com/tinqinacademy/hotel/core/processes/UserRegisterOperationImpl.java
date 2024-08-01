package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.exceptions.QueryException;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterOperation;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterOutput;
import com.tinqinacademy.hotel.persistence.entities.GuestEntity;
import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import com.tinqinacademy.hotel.persistence.repositorynew.GuestRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.UserRepository;
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

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserRegisterOperationImpl implements UserRegisterOperation {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final UserRepository userRepository;

    @Override
    public Either<ErrorsProcessor, UserRegisterOutput> process(UserRegisterInput input) {
        return Try.of(() -> {
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
                .mapLeft(throwable -> API.Match(throwable).of(
                        Case($(instanceOf(InputException.class)), e -> {
                            log.error("Invalid input: {}", e.getMessage());
                            return ErrorsProcessor.builder()
                                    .httpStatus(HttpStatus.NOT_FOUND)
                                    .statusCode(HttpStatus.NOT_FOUND.value())
                                    .message(e.getMessage())
                                    .build();
                        }),
                        Case($(instanceOf(QueryException.class)), e -> {
                            log.error("Invalid query: {}", e.getMessage());
                            return ErrorsProcessor.builder()
                                    .httpStatus(HttpStatus.NOT_FOUND)
                                    .statusCode(HttpStatus.NOT_FOUND.value())
                                    .message(e.getMessage())
                                    .build();
                        })

                ));
    }

}
