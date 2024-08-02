package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.exceptions.QueryException;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookInput;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookOperation;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookOutput;
import com.tinqinacademy.hotel.core.exceptionfamilies.InputQueryExceptionCase;
import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.entities.UserEntity;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserBookOperationImpl extends BaseOperation<UserBookOutput,UserBookInput> implements UserBookOperation {


    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public Either<ErrorsProcessor, UserBookOutput> process(UserBookInput input) {
        log.info("Start book room: {}", input);
        return Try.of(()->{
            Long year= ageCheck(input.getDateOfBirth());
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
                .mapLeft(InputQueryExceptionCase::handleThrowable);
    }
    private Long ageCheck(LocalDate birthday){
        Long year= ChronoUnit.YEARS.between(birthday, LocalDate.now());
        if(year<18L){
            throw new InputException("User is not old enough");
        }
        return year;
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
