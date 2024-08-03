package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookInput;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookOperation;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryExceptionCase;
import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.entities.UserEntity;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;


@Service
@Slf4j
public class UserBookOperationImpl extends BaseProcess implements UserBookOperation {


    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserBookOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator, ReservationRepository reservationRepository, RoomRepository roomRepository, UserRepository userRepository) {
        super(conversionService, errorMapper, validator);
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Either<ErrorsProcessor, UserBookOutput> process(@Valid UserBookInput input) {
        log.info("Start book room: {}", input);
        return validateInput(input).flatMap(validInput -> Try.of(()->{
            ageCheck(input.getDateOfBirth());
            Long daysAtHotel=daysAtHotelCheck(input.getStartDate(), input.getEndDate());
            emailCheck(input.getEmail());
            RoomEntity roomEntity = getRoomCheck(input);
            UserEntity userEntity = UserEntity.builder()
                    .email(input.getEmail())
                    .firstName(input.getFirstName())
                    .lastName(input.getLastName())
                    .phoneNumber(input.getPhoneNo())
                    .birthday(input.getDateOfBirth())
                    .build();
            userRepository.save(userEntity);

            ReservationEntity reservationEntity = ReservationEntity.builder()
                    .room(roomRepository.getReferenceById(input.getRoomID()))
                    .price(BigDecimal.valueOf(daysAtHotel).multiply(roomRepository.getReferenceById(input.getRoomID()).getPrice()))
                    .endDate(input.getEndDate())
                    .startDate(input.getStartDate())
                    .room(roomEntity)
                    .user(userEntity)
                    .build();

            reservationRepository.save(reservationEntity);
            UserBookOutput userBookOutput = UserBookOutput.builder()
                    .message("Successfully booked a room")
                    .build();
            log.info("End book room: {}", userBookOutput);
            return userBookOutput;
        })
                .toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
    private void ageCheck(LocalDate birthday){
        Long year= ChronoUnit.YEARS.between(birthday, LocalDate.now());
        if(year<18L){
            throw new InputException("User is not old enough");
        }
    }
    private Long daysAtHotelCheck(LocalDate startDate, LocalDate endDate){
        Long daysAtHotel=ChronoUnit.DAYS.between(startDate, endDate);
        if (daysAtHotel<1){
            throw new InputException("Days at hotel are negative");
        }
        return daysAtHotel;
    }
    private void emailCheck(String email){
        if(userRepository.getAllEmails().contains(email)){
            throw new InputException("The following email is taken!");
        }
    }
    private RoomEntity getRoomCheck(UserBookInput input){
        List<UUID> roomIDs=reservationRepository.findBetweenStartDateAndEndDate(input.getStartDate(), input.getEndDate());
        if(roomIDs.contains(input.getRoomID())){
            throw new InputException("The following roomID is taken for that period");
        }
        return roomRepository.getReferenceById(input.getRoomID());
    }

}
